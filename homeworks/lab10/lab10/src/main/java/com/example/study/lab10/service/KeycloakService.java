package com.example.study.lab10.service;

import com.example.study.lab10.dto.RegisterRequest;
import com.example.study.lab10.dto.SessionResponse;
import com.example.study.lab10.model.entity.Customer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CustomerService customerService;

    @Value("${keycloak.auth-server-url}")
    private String keycloakBaseUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-resource}")
    private String adminId;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String adminUsername;

    @Value("${keycloak.password}")
    private String adminPassword;

    public String getAdminToken() {
        String url = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", adminId);
        form.add("username", adminUsername);
        form.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("access_token").asText();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse admin token", e);
            }
        } else {
            throw new RuntimeException("Failed to get admin token: " + response.getBody());
        }
    }

    public String createUser(RegisterRequest dto, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> user = new HashMap<>();
        user.put("username", dto.getUsername());
        user.put("email", dto.getEmail());
        user.put("enabled", true);
        user.put("firstName", dto.getFirstName());
        user.put("lastName", dto.getLastName());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("User {} created in Keycloak", dto.getUsername());
            String userId = getUserId(dto.getUsername(), token);
            resetPassword(userId, dto.getPassword(), token); // Установить роль и пароль
            assignRoleToUser(userId, dto.getRole(), token);
            return userId;
        } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
            log.warn("User {} already exists", dto.getUsername());
            return getUserId(dto.getUsername(), token);
        } else {
            throw new RuntimeException("Failed to create user: " + response.getStatusCode());
        }
    }

    public SessionResponse authenticateUser(String username, String password) {
        String url = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode json = objectMapper.readTree(response.getBody());
                return SessionResponse.builder()
                        .accessToken(json.get("access_token").asText())
                        .refreshToken(json.get("refresh_token").asText())
                        .tokenType(json.get("token_type").asText())
                        .expiresIn(json.get("expires_in").asInt())
                        .refreshExpiresIn(json.get("refresh_expires_in").asInt())
                        .build();
            } else {
                throw new RuntimeException("Failed to authenticate: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during authentication", e);
        }
    }

    public void terminate(String refreshToken) {
        String url = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Logout failed: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during logout", e);
        }
    }

    public Long getUserIdFromJwt(Jwt jwt) {
        String sub = jwt.getSubject();
        Customer customer = customerService.findCustomerByExternalId(sub); // customer is null!
        return customer.getId();
    }

    public boolean hasRole(Jwt jwt, String role) {
        var roles = jwt.getClaimAsMap("realm_access");
        if (roles == null || !roles.containsKey("roles")) return false;

        List<String> roleList = (List<String>) roles.get("roles");
        return roleList.contains(role);
    }

    private String getUserId(String username, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            if (json.isArray() && !json.isEmpty()) {
                return json.get(0).get("id").asText();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get userId", e);
        }
        throw new RuntimeException("User not found in Keycloak");
    }

    private void assignRoleToUser(String userId, String roleName, String token) {
        String roleUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/roles/" + roleName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Получаем представление роли
        ResponseEntity<Map> roleResponse = restTemplate.exchange(
                roleUrl, HttpMethod.GET, new HttpEntity<>(headers), Map.class
        );

        if (!roleResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch role: " + roleName);
        }

        Map<String, Object> role = roleResponse.getBody();

        // Назначаем роль пользователю
        String assignUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

        HttpEntity<List<Map<String, Object>>> assignRequest = new HttpEntity<>(
                List.of(role), headers
        );

        restTemplate.postForEntity(assignUrl, assignRequest, Void.class);
    }

    private void resetPassword(String userId, String password, String token) {
        String url = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> passwordPayload = new HashMap<>();
        passwordPayload.put("type", "password");
        passwordPayload.put("temporary", false);
        passwordPayload.put("value", password);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(passwordPayload, headers);
        restTemplate.put(url, request);

        log.info("Password set for user {}", userId);
    }
}
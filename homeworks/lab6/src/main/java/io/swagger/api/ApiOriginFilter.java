package io.swagger.api;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Generated;
import java.io.IOException;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-25T14:57:37.893068418Z[GMT]")
public class ApiOriginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        res.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Пустая реализация, так как нет ресурсов для освобождения
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Пустая реализация, так как не требуется инициализация
    }
}
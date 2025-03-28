package com.sibsutis.study.test3;

import com.sibsutis.study.test3.model.entity.Institute;
import com.sibsutis.study.test3.model.entity.University;
import com.sibsutis.study.test3.repository.InstituteRepository;
import com.sibsutis.study.test3.repository.UniversityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Test4Application {

	public static void main(String[] args) {
		SpringApplication.run(Test4Application.class, args);

	}

	@Bean
	public CommandLineRunner demo(UniversityRepository universityRepository,
								  InstituteRepository instituteRepository) {
		return (args) -> {
			University university = new University();
			university.setName("СибГУТИ");
			university.setPhone("+7 (495) 123-45-67");

			University savedUniversity = universityRepository.save(university);
			System.out.println("Университет сохранен: " + savedUniversity.getName());

			Institute institute1 = new Institute();
			institute1.setName("ИВТ");
			institute1.setPhone("+7 (495) 111-11-11");
			institute1.setUniversity(savedUniversity);

			Institute institute2 = new Institute();
			institute2.setName("ИТ");
			institute2.setPhone("+7 (495) 222-22-22");
			institute2.setUniversity(savedUniversity);

			instituteRepository.save(institute1);
			instituteRepository.save(institute2);
			System.out.println("Институты сохранены");

			System.out.println("Все университеты:");
			universityRepository.findAll().forEach(System.out::println);

			System.out.println("Все институты:");
			instituteRepository.findAll().forEach(System.out::println);
		};
	}
}

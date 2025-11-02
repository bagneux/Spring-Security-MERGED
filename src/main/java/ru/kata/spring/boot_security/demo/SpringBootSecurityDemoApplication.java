package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);

		// Получение сервиса по бину
		UserService userService = context.getBean(UserService.class);

		// Получение роли (по ID, например)
		//RoleRepository roleRepository = context.getBean(RoleRepository.class);
		//Role adminRole = roleRepository.findById(1L);


		// Создаем и сохраняем пользователя
		//userService.saveUser("admin", "admin123", Set.of(adminRole));
	}
}

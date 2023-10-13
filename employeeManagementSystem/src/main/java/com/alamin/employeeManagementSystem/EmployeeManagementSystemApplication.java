package com.alamin.employeeManagementSystem;

import com.alamin.employeeManagementSystem.app_security.model.Role;
import com.alamin.employeeManagementSystem.app_security.model.User;
import com.alamin.employeeManagementSystem.app_security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			var user = User.builder()
					.firstname("Al Amin")
					.lastname("Rony")
					.email("user@gmail.com")
					.password(passwordEncode.encode("password"))
					.role(Role.USER)
					.build();
			userRepository.save(user);
		};
	}

}

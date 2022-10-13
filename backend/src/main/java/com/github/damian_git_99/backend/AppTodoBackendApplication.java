package com.github.damian_git_99.backend;

import com.github.damian_git_99.backend.user.role.Role;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppTodoBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AppTodoBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleRepository.save(new Role("USER"));
	}
}

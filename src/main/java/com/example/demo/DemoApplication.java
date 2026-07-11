package com.example.demo;

import com.example.demo.config.AppConstants;
import com.example.demo.entities.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Bean
	public CommandLineRunner commandLineRunner(){
		return (args)-> {
			System.out.println("this is bean command line runner");
		};
	}


	@Autowired
	private RoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//we will create some default user role
		//ADMIN
		//GUEST

		roleRepository.findByName("ROLE_" + AppConstants.ADMIN_ROLE).ifPresentOrElse(role -> {
			System.out.println("Admin Role Already Exists: " + role.getName());
		}, () -> {

			Role role = new Role();
			role.setName("ROLE_" + AppConstants.ADMIN_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);

		});

		roleRepository.findByName("ROLE_" + AppConstants.GUEST_ROLE).ifPresentOrElse(role -> {
			System.out.println("Guest Role Already Exists: " + role.getName());
		}, () -> {

			Role role = new Role();
			role.setName("ROLE_" + AppConstants.GUEST_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);

		});
	}

}

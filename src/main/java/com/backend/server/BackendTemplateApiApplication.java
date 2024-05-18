package com.backend.server;

import com.backend.server.contexts.shared.infrastructure.jwt.JwtUtils;
import com.backend.server.contexts.shared.infrastructure.utils.UserDataUtil;

import com.backend.server.contexts.users.domain.clazz.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class BackendTemplateApiApplication implements ApplicationRunner {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Environment env;

	@Autowired
	private JwtUtils jwtUtils;

	public static void main(String[] args) {
		SpringApplication.run(BackendTemplateApiApplication.class, args);
	}

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		PasswordEncoder encoder = encoder();
		User user = UserDataUtil.create();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setToken(jwtUtils.generateJwtToken(user.getEmail()));
		entityManager.createQuery(env.getProperty("app.init-sql"))
			.setParameter(1, user.getId())
			.setParameter(2, user.getName())
			.setParameter(3, user.getEmail())
			.setParameter(4, user.getPassword())
			.setParameter(5, user.getIsActive())
			.setParameter(6, user.getToken())
			.setParameter(7, user.getCreated())
			.setParameter(8, user.getModified())
			.setParameter(9, user.getLastLogin())
		.executeUpdate();
	}
}

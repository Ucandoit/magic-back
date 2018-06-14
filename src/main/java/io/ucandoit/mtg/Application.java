package io.ucandoit.mtg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application extends SpringBootServletInitializer {

	private final static String SPRING_PROFILE_DEVELOPMENT = "dev";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.profiles(addDefaultProfile(null)).sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setAdditionalProfiles(addDefaultProfile(new SimpleCommandLinePropertySource(args)));
		app.run(args);
	}

	/**
	 * * Add a default profile. *
	 * <p>
	 * * Please use -Dspring.profiles.active=xxx *
	 * </p>
	 */
	private static String addDefaultProfile(SimpleCommandLinePropertySource source) {
		String profile;
		if (source != null && source.containsProperty("spring.profiles.active")) {
			profile = source.getProperty("spring.profiles.active");
		} else {
			profile = System.getProperty("spring.profiles.active");
		}
		if (profile != null) {
			log.info("Running with Spring profile(s) : {}", profile);
			return profile;
		} else {
			log.warn("No Spring profile configured, running with development configuration");
			return SPRING_PROFILE_DEVELOPMENT;
		}
	}
}

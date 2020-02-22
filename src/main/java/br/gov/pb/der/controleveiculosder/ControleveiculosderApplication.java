package br.gov.pb.der.controleveiculosder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ControleveiculosderApplication extends ServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<ControleveiculosderApplication> applicationClass = ControleveiculosderApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(ControleveiculosderApplication.class, args);
	}
}

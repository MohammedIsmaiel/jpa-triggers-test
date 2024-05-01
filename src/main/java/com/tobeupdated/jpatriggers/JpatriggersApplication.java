package com.tobeupdated.jpatriggers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "jpa with listeners test", version = "1.0"))

public class JpatriggersApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpatriggersApplication.class, args);
	}

}

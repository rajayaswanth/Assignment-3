package com.mla.htd;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Assignment3Application {

	public static void main(String[] args) {
		SpringApplication.run(Assignment3Application.class, args);
	}

}

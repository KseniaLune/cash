package com.example.cash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CashTasklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashTasklistApplication.class, args);
	}

}

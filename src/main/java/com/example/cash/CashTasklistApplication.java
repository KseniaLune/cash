package com.example.cash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class CashTasklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashTasklistApplication.class, args);
	}

}

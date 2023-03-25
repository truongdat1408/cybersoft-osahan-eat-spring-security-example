package com.cybersoft.osahaneat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.cybersoft.osahaneat")
public class OsahanEatApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsahanEatApplication.class, args);
	}

}

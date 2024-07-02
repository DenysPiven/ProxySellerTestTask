package com.proxyseller.twitter

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.proxyseller.twitter"])
class TwitterApplication {

	static void main(String[] args) {
		SpringApplication.run(TwitterApplication, args)
	}
}

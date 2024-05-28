package com.project.beathub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@EnableCaching
public class BeathubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeathubApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home() {
		return "BeatHub";
	}

}
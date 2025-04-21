package cn.edu.xjtlu.readingnotes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import cn.edu.xjtlu.readingnotes.storage.StorageProperties;
import cn.edu.xjtlu.readingnotes.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ReadingnotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingnotesApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}

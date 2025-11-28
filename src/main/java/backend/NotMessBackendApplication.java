package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotMessBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotMessBackendApplication.class, args);
	}

}

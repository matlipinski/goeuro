package pl.goeuro.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BusApplication {

	@Autowired
	private DirectRouteVerifier directRouteVerifier;
	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}
}

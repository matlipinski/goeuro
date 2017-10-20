package pl.goeuro.bus;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class DirectRouteVerifier {

	@Value("${routes.file.path}")
	private String routesFilePath;

	@PostConstruct
	private void postConstruct(){
		log.info("File path: {}", routesFilePath);
	}
}

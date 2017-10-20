package pl.goeuro.bus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
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
		verify("0", "5");
	}

	boolean verify(String start, String end){
		long startTime = System.nanoTime();
		log.debug("Try to find router for between stations: {} - {}", start, end);
		Path file = Paths.get(routesFilePath);
		try
		{
			Stream<String> lines = Files.lines( file, StandardCharsets.UTF_8 ).skip(1);
			//final String totalRows = lines.findFirst().get();
			//log.info("Total rows in file : {}", totalRows);
			for(String line : (Iterable<String>)lines::iterator){
				final String[] lineElements = line.split(" ");
				boolean foundStart = false;
				String searchElement = start;
				for(int i =1 ; i< lineElements.length; i++){

					if(lineElements[i].equals(searchElement)){
						if(foundStart){
							log.info("Found route");
							return true;
						}
						searchElement = end;
						foundStart = true;
					}
				}
				log.info(line);
			}
		} catch (IOException ex){
			log.error("Error while reading file with routes: {}", ex);
		}

		long endTime = System.nanoTime();
		long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
		log.info("Total elapsed time: " + elapsedTimeInMillis + " ms");
		return false;
	}
}

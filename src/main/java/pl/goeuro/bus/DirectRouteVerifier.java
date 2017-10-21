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
    private void postConstruct() {
        log.info("File path: {}", routesFilePath);
    }

    Route verify(int departureStationId, int arrivalStationId) {
        String departure = String.valueOf(departureStationId);
        String arrival = String.valueOf(arrivalStationId);
        log.debug("Try to find router for between stations: {} - {}", departure, arrival);
        Path filePath = Paths.get(routesFilePath);
        boolean foundDirectRoute = false;
        try {
            foundDirectRoute = Files.lines(filePath, StandardCharsets.UTF_8)
                    .skip(1)
                    .map(route -> route.split(" "))
                    .anyMatch(route -> findRoute(route, departure, arrival));

        } catch (IOException ex) {
            log.error("Error while reading file with routes: {}", ex);
        }

        return Route.builder()
                .arrivalStationId(departureStationId)
                .departureStationId(arrivalStationId)
                .directBusRoute(foundDirectRoute)
                .build();
    }

    private boolean findRoute(String[] busStations, String departure, String arrival) {
        boolean alreadyFoundDeparture = false;
        String stationToFind = departure;
        for (int i = 1; i < busStations.length; i++) {
            if (busStations[i].equals(stationToFind)) {
                if (alreadyFoundDeparture) {
                    log.info("Found route for departure:{}, arrival: {}", departure, arrival);
                    return true;
                }
                stationToFind = arrival;
                alreadyFoundDeparture = true;
            }
        }
        return false;
    }
}

package pl.goeuro.bus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Slf4j
class RouteFileParser {
    private final static Pattern SPACE_REGEXP = Pattern.compile("\\s+");

    @Value("${routes.file.path}")
    private String routesFilePath;

    Map<Integer, TreeSet<Integer>> parse(){
        Path filePath = Paths.get(routesFilePath);
        Map<Integer, TreeSet<Integer>> routes= new HashMap<>();
        try {

            Files.lines(filePath, StandardCharsets.UTF_8)
                    .skip(1)
                    .map(this::parseToInt)
                    .filter(route -> !route.isEmpty())
                    .forEach(route -> convertRoute(route, routes));
            log.info("Route file parsed successfully");

        } catch (IOException ex) {
            log.error("Error while reading file with routes: {}", ex);
        }

        return routes;
    }

    private void convertRoute(List<Integer> route, Map<Integer, TreeSet<Integer>> routes) {
        Integer routeId = route.get(0);
        route.stream()
                .skip(1)
                .forEach(stationId ->
                        routes.computeIfAbsent(stationId, k -> new TreeSet<>()).add(routeId));
    }

    private List<Integer> parseToInt(String route) {
        return SPACE_REGEXP.splitAsStream(route)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}

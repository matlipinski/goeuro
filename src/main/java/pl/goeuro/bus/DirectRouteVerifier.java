package pl.goeuro.bus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

@Service
@Slf4j
class DirectRouteVerifier {

    private final RouteRepository routeRepository;

    @Autowired
    DirectRouteVerifier(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }


    Route verify(int departureStationId, int arrivalStationId) {

        log.debug("Try to find router for between stations: {} - {}", departureStationId, arrivalStationId);

        if (Objects.equals(departureStationId, arrivalStationId)) {
            return new Route(departureStationId, arrivalStationId, true);
        }

        TreeSet departureRoutes = routeRepository.getRoutes().getOrDefault(departureStationId, new TreeSet<>());
        TreeSet arrivalRoutes = routeRepository.getRoutes().getOrDefault(arrivalStationId, new TreeSet<>());

        boolean hasCommonStationId = !Collections.disjoint(departureRoutes, arrivalRoutes);
        log.debug("Between stations {} - {}, has direct route: {}",
                departureStationId, arrivalStationId, hasCommonStationId);
        return new Route(departureStationId, arrivalStationId, hasCommonStationId);
    }
}

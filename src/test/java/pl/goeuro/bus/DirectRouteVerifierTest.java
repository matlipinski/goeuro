package pl.goeuro.bus;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class DirectRouteVerifierTest {


    @Test
    public void shouldFoundRoute(){
        //given
        RouteRepository routeRepository = mock(RouteRepository.class);
        when(routeRepository.getRoutes()).thenReturn(prepareRoutes());
        DirectRouteVerifier directRouteVerifier = new DirectRouteVerifier(routeRepository);

        //when
        Route route = directRouteVerifier.verify(0, 4);

        //then
        assertThat(route.isDirectBusRoute()).isTrue();
        assertThat(route.getDepartureStationId()).isEqualTo(0);
        assertThat(route.getArrivalStationId()).isEqualTo(4);
    }

    @Test
    public void shouldNotFoundRoute(){
        //given
        RouteRepository routeRepository = mock(RouteRepository.class);
        when(routeRepository.getRoutes()).thenReturn(prepareRoutes());
        DirectRouteVerifier directRouteVerifier = new DirectRouteVerifier(routeRepository);

        //when
        Route route = directRouteVerifier.verify(8, 4);

        //then
        assertThat(route.isDirectBusRoute()).isFalse();
        assertThat(route.getDepartureStationId()).isEqualTo(8);
        assertThat(route.getArrivalStationId()).isEqualTo(4);
    }

    /**
     * Prepares routes for file:
     * 3
     * 0 0 1 2 3 4
     * 1 3 1 6 5
     * 2 0 6 4
     */
    private Map<Integer, TreeSet<Integer>> prepareRoutes() {
        Map<Integer, TreeSet<Integer>> routes = new HashMap<>();
        routes.put(0, new TreeSet<>(Arrays.asList(0,2)));
        routes.put(1, new TreeSet<>(Arrays.asList(0,1)));
        routes.put(2, new TreeSet<>(Collections.singletonList(0)));
        routes.put(3, new TreeSet<>(Arrays.asList(0,1)));
        routes.put(4, new TreeSet<>(Arrays.asList(0,2)));
        routes.put(5, new TreeSet<>(Collections.singletonList(1)));
        routes.put(6, new TreeSet<>(Arrays.asList(1,2)));
        return routes;
    }
}

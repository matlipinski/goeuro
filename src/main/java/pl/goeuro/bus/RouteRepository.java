package pl.goeuro.bus;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeSet;

@Repository
class RouteRepository {

    final RouteFileParser routeFileParser;

    @Getter
    private Map<Integer, TreeSet<Integer>> routes;


    @Autowired
    public RouteRepository(RouteFileParser routeFileParser) {
        this.routeFileParser = routeFileParser;
    }

    @PostConstruct
    private void fillRoutes() {
        routes = routeFileParser.parse();
    }
}

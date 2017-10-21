package pl.goeuro.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DirectRouteController {

    private final DirectRouteVerifier directRouteVerifier;

    @Autowired
    DirectRouteController(DirectRouteVerifier directRouteVerifier) {
        this.directRouteVerifier = directRouteVerifier;
    }

    @RequestMapping("/direct")
    Route verifyIfDirectRouteExists(@RequestParam(value = "dep_sid") int departureStationId,
                                    @RequestParam(value = "arr_sid") int arrivalStationId) {
        return directRouteVerifier.verify(departureStationId, arrivalStationId);
    }


}

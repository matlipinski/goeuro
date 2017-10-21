package pl.goeuro.bus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
class Route {

    @JsonProperty("dep_sid")
    private int departureStationId;
    @JsonProperty("arr_sid")
    private int arrivalStationId;
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;
}

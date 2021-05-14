package org.vitrivr.cineast.core.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.vitrivr.cineast.core.data.tag.IncompleteTag;

import java.util.List;

public class Circle {

    private final String type;
    private final double lat, lon, rad;

    /**
     * Constructor for {@link Circle}. Used to create object from JSON.
     *
     * @param type       The type of the circle.
     * @param lat        The latitude of the circle.
     * @param lon        The longitude of the circle.
     * @param rad        The radius of the circle.
     */
    @JsonCreator
    public Circle(@JsonProperty(value = "type") String type,
                         @JsonProperty(value = "lat") double lat,
                         @JsonProperty(value = "lon") double lon,
                         @JsonProperty(value = "rad") double rad) {
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.rad = rad;
    }
}

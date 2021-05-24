package org.vitrivr.cineast.core.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.vitrivr.cineast.core.data.tag.IncompleteTag;

import java.util.List;

public class Circle {

    private final String type, semantic_name;
    private final double lat, lon, rad;

    /**
     * Constructor for {@link Circle}. Used to create object from JSON.
     *
     * @param type       The type indicates if a circle is drawn or if a location was given as tag. 'circle' or 'info'
     * @param lat        The latitude of the circle.
     * @param lon        The longitude of the circle.
     * @param rad        The radius of the circle.
     * @param semantic_name The name of the location, when circe is drawn.
     */
    @JsonCreator
    public Circle(@JsonProperty(value = "type") String type,
                         @JsonProperty(value = "lat") double lat,
                         @JsonProperty(value = "lon") double lon,
                         @JsonProperty(value = "rad") double rad,
                         @JsonProperty(value = "semantic_name") String semantic_name) {
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.rad = rad;
        this.semantic_name = semantic_name;
        System.out.println("type = " + this.type);
        System.out.println("lat = " + this.lat);
        System.out.println("lon = " + this.lon);
        System.out.println("rad = " + this.rad);
        System.out.println("semantic_name = " + this.semantic_name);
    }
}

package org.vitrivr.cineast.core.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * An instance of this class saves the information of one (in the UI) drawn circle or pin.
 */
public class Circle {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String type, semantic_name;
    private final double lat, lon, rad;

    /**
     * Constructor for {@link Circle}. Used to create object from JSON.
     *
     * @param type       The type indicates if it is a drawn circle or a pin. 'circle' or 'info'
     * @param lat        The latitude of the circle/pin.
     * @param lon        The longitude of the circle/pin.
     * @param rad        The radius of the drawn circle.
     * @param semantic_name The name of the location, where the pin is created.
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
        /*LOGGER.debug("type = " + this.type);
        LOGGER.debug("lat = " + this.lat);
        LOGGER.debug("lon = " + this.lon);
        LOGGER.debug("rad = " + this.rad);
        LOGGER.debug("semantic_name = " + this.semantic_name);*/
    }
    public double getLat() {
        return this.lat;
    }

    public double getLon() {
        return this.lon;
    }

    public double getRad() {
        return this.rad;
    }

    public String getType() {
        return this.type;
    }
}
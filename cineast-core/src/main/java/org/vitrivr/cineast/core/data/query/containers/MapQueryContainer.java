package org.vitrivr.cineast.core.data.query.containers;

import org.vitrivr.cineast.core.data.Circle;
import org.vitrivr.cineast.core.util.json.JacksonJsonProvider;
import org.vitrivr.cineast.core.util.web.DataURLParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapQueryContainer extends QueryContainer { // vitrivr pendant: MapQueryTerm

    private List<Circle> regions;

    public MapQueryContainer(String data) {
        final JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        final String converted = DataURLParser.dataURLtoString(data, "application/json");
        final Circle[] locations = jsonProvider.toObject(converted, Circle[].class);
        if (locations != null) {
            this.regions = Arrays.asList(locations);
        } else {
            this.regions = new ArrayList<>(0);
        }
    }

    @Override
    public List<Circle> getRegions() {
        return this.regions;
    }
}

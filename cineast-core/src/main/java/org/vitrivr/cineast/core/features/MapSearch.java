package org.vitrivr.cineast.core.features;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vitrivr.cineast.core.config.QueryConfig;
import org.vitrivr.cineast.core.config.ReadableQueryConfig;
import org.vitrivr.cineast.core.data.Circle;
import org.vitrivr.cineast.core.data.score.ScoreElement;
import org.vitrivr.cineast.core.data.segments.SegmentContainer;
import org.vitrivr.cineast.core.db.DBSelectorSupplier;
import org.vitrivr.cineast.core.features.abstracts.AbstractFeatureModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapSearch extends AbstractFeatureModule {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String LOCATION_TABLE_NAME = "features_spatialdistance";

    public MapSearch() {
        super(LOCATION_TABLE_NAME, 10000, 2);
    }

    @Override
    public void processSegment(SegmentContainer shot) {

    }

    @Override
    public List<ScoreElement> getSimilar(SegmentContainer sc, ReadableQueryConfig qc) {
        List<Circle> circles = sc.getRegions();
        if (circles.isEmpty()) {

        }
        LOGGER.debug(sc.getRegions());

        HashMap<String, ScoreElement> distinctElements = new HashMap<>();
        ReadableQueryConfig qc_new = new QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.haversine);
        for (Circle circle: circles) {
            double lat = circle.getLat();
            double lon = circle.getLon();
            float[] vec = {(float)lat, (float)lon};
            LOGGER.debug("Check fractionScoreElements");
            List<ScoreElement> fractionScoreElements = getSimilar(vec, qc_new);
            for (ScoreElement e: fractionScoreElements) {
                distinctElements.put(e.getId(), e);
                LOGGER.debug(e.getId());
            }
        }
        List<ScoreElement> scoreElements = new ArrayList<ScoreElement>();
        scoreElements.addAll(distinctElements.values()); // insert all distinct ScoreElements
        LOGGER.debug("Check scoreElements");
        for (ScoreElement e: scoreElements) {
            LOGGER.debug(e.getId());
        }
        return scoreElements; //getSimilar(,QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.haversine));
    }
}

package org.vitrivr.cineast.core.features;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vitrivr.cineast.core.config.QueryConfig;
import org.vitrivr.cineast.core.config.ReadableQueryConfig;
import org.vitrivr.cineast.core.data.Circle;
import org.vitrivr.cineast.core.data.CorrespondenceFunction;
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
    private static final String MARKER_TYPE = "info";

    public MapSearch() {
        super(LOCATION_TABLE_NAME, 200, 2);
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
        ReadableQueryConfig qc_new;
        HashMap<String, ScoreElement> distinctElements = new HashMap<>();
        for (Circle circle: circles) {
            // if marker: rad is 0
            if (circle.getType().equals(MARKER_TYPE)) {
                qc_new = new QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.haversine);
            } else {
                qc_new = new QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.haversine).setCorrespondenceFunction(CorrespondenceFunction.linear(circle.getRad()));
            }
            double lat = circle.getLat();
            double lon = circle.getLon();
            float[] vec = {(float)lat, (float)lon};
            LOGGER.debug("Check fractionScoreElements");
            List<ScoreElement> fractionScoreElements = getSimilar(vec, qc_new);
            for (ScoreElement e: fractionScoreElements) {
                if (distinctElements.containsKey(e.getId())) {
                    if (e.getScore() > distinctElements.get(e.getId()).getScore()) { // has the new element a bigger score, than the scoreelement in the list?
                        distinctElements.put(e.getId(), e);
                    }
                } else {
                    distinctElements.put(e.getId(), e);
                }
                // LOGGER.debug(e.getId());
            }
        }
        List<ScoreElement> scoreElements = new ArrayList<>();
        scoreElements.addAll(distinctElements.values()); // insert all distinct ScoreElements
        LOGGER.debug("Check scoreElements");
        for (ScoreElement e: scoreElements) {
            LOGGER.debug(e.getId());
        }
        return scoreElements;
    }
}

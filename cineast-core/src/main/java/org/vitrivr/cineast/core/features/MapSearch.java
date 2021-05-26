package org.vitrivr.cineast.core.features;

import org.vitrivr.cineast.core.config.QueryConfig;
import org.vitrivr.cineast.core.config.ReadableQueryConfig;
import org.vitrivr.cineast.core.data.Circle;
import org.vitrivr.cineast.core.data.score.ScoreElement;
import org.vitrivr.cineast.core.data.segments.SegmentContainer;
import org.vitrivr.cineast.core.db.DBSelectorSupplier;
import org.vitrivr.cineast.core.features.abstracts.AbstractFeatureModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapSearch extends AbstractFeatureModule {

    public static final String LOCATION_TABLE_NAME = "features_spatialdistance";

    public MapSearch() {
        super(LOCATION_TABLE_NAME, 5000, 2);
    }

    @Override
    public void processSegment(SegmentContainer shot) {

    }

    @Override
    public List<ScoreElement> getSimilar(SegmentContainer sc, ReadableQueryConfig qc) {
       /* sc.getRegions();
        float[] vector = new float[]
        return this.getSimilar()*/
        List<Circle> circles = sc.getRegions();
        if (circles.isEmpty()) {

        }
        List<ScoreElement> scoreElements = new ArrayList();
        System.out.println(sc.getRegions());
        for (Circle circle: circles) {
            double lat = circle.getLat();
            double lon = circle.getLon();
            float[] vec = {(float)lat, (float)lon};
            ReadableQueryConfig qc_new = new QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.euclidean);
            scoreElements.addAll(getSimilar(vec, qc_new));
        }
        // new QueryConfig(qc).setDistanceWeights(p.second)
        // return new QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.euclidean);
        return scoreElements;//getSimilar(,QueryConfig(qc).setDistanceIfEmpty(QueryConfig.Distance.haversine));

    }
}

package org.vitrivr.cineast.core.data.providers;

import org.vitrivr.cineast.core.data.Circle;
import org.vitrivr.cineast.core.data.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MapSearchProvider {
  default List<Circle> getRegions() {
    return new ArrayList<>(0);
  }
}

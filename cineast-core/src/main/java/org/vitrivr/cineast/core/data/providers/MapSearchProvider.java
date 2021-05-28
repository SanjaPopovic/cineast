package org.vitrivr.cineast.core.data.providers;

import org.vitrivr.cineast.core.data.Circle;
import java.util.ArrayList;
import java.util.List;

public interface MapSearchProvider {
  default List<Circle> getRegions() {
    return new ArrayList<>(0);
  }
}

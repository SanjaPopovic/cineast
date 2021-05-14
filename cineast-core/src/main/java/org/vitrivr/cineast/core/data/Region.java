package org.vitrivr.cineast.core.data;

import java.util.List;

public class Region {
    enum Type {
        CIRCLE, PATH;
    }
    public String type;
    public List<Location> latlngs;


    public class Circle extends Region {
        public Circle(Location latlng) {
            this.type = Type.CIRCLE.toString();
            this.latlngs.add(latlng);
        }
    }

    public class Path extends Region {
        public Path(List<Location> latlngs) {
            this.type = Type.PATH.toString();
            this.latlngs = latlngs;
        }
    }
}



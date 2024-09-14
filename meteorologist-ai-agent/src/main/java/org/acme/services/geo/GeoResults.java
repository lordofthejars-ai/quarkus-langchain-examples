package org.acme.services.geo;

import java.util.List;

public record GeoResults(List<GeoResult> results) {

    public GeoResult getFirst() {
        return results.getFirst();
    }

}

package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitStops {

    private List<Stop> stops = null;
    private Meta meta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

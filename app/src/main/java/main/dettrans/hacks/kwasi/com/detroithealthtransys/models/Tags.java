package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class Tags {

    private String zoneId;
    private String osmWayId;
    private String stopDesc;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getOsmWayId() {
        return osmWayId;
    }

    public void setOsmWayId(String osmWayId) {
        this.osmWayId = osmWayId;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

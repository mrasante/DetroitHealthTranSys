
package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleStopPairs {

    private List<ScheduleStopPair> scheduleStopPairs = null;
    private Meta meta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ScheduleStopPairs() {
    }

    /**
     * 
     * @param scheduleStopPairs
     * @param meta
     */
    public ScheduleStopPairs(List<ScheduleStopPair> scheduleStopPairs, Meta meta) {
        super();
        this.scheduleStopPairs = scheduleStopPairs;
        this.meta = meta;
    }

    public List<ScheduleStopPair> getScheduleStopPairs() {
        return scheduleStopPairs;
    }

    public void setScheduleStopPairs(List<ScheduleStopPair> scheduleStopPairs) {
        this.scheduleStopPairs = scheduleStopPairs;
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

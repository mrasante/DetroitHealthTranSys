package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class ImportedFromFeed {

    private String feedOnestopId;
    private String feedVersionSha1;
    private Object gtfsId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getFeedOnestopId() {
        return feedOnestopId;
    }

    public void setFeedOnestopId(String feedOnestopId) {
        this.feedOnestopId = feedOnestopId;
    }

    public String getFeedVersionSha1() {
        return feedVersionSha1;
    }

    public void setFeedVersionSha1(String feedVersionSha1) {
        this.feedVersionSha1 = feedVersionSha1;
    }

    public Object getGtfsId() {
        return gtfsId;
    }

    public void setGtfsId(Object gtfsId) {
        this.gtfsId = gtfsId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

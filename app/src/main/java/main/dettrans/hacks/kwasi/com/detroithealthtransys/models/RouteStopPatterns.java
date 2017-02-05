
package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteStopPatterns implements Serializable
{

    private List<RouteStopPattern> route_stop_patterns = null;
    private Meta meta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1643888968862019764L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RouteStopPatterns() {
    }

    /**
     * 
     * @param meta
     * @param route_stop_patterns
     */
    public RouteStopPatterns(List<RouteStopPattern> route_stop_patterns, Meta meta) {
        super();
        this.route_stop_patterns = route_stop_patterns;
        this.meta = meta;
    }

    public List<RouteStopPattern> getRouteStopPatterns() {
        return route_stop_patterns;
    }

    public void setRouteStopPatterns(List<RouteStopPattern> routeStopPatterns) {
        this.route_stop_patterns = routeStopPatterns;
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

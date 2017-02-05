package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class RoutesServingStop {

    private String operatorName;
    private String operatorOnestopId;
    private String routeName;
    private String routeOnestopId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorOnestopId() {
        return operatorOnestopId;
    }

    public void setOperatorOnestopId(String operatorOnestopId) {
        this.operatorOnestopId = operatorOnestopId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteOnestopId() {
        return routeOnestopId;
    }

    public void setRouteOnestopId(String routeOnestopId) {
        this.routeOnestopId = routeOnestopId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteGeometry implements Serializable
{

    private String type;
    private List<List<Double>> coordinates = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5355707127054201714L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RouteGeometry() {
    }

    /**
     * 
     * @param type
     * @param coordinates
     */
    public RouteGeometry(String type, List<List<Double>> coordinates) {
        super();
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

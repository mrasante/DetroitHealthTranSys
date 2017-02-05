
package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteStopPattern implements Serializable
{

    private List<String> identifiers = null;
    private List<ImportedFromFeed> imported_from_feeds = null;
    private int created_or_updated_in_changeset_id;
    private String onestop_id;
    private String route_onestop_id;
    private List<String> stop_pattern = null;
    private List<Double> stop_distances = null;
    private RouteGeometry geometry;
    private String color;
    private boolean is_generated;
    private boolean is_modified;
    private String created_at;
    private String updated_at;
    private List<String> trips = null;
    private Tags tags;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 2935699105173569528L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RouteStopPattern() {
    }

    /**
     * 
     * @param tags
     * @param stop_distances
     * @param is_generated
     * @param is_modified
     * @param trips
     * @param identifiers
     * @param route_onestop_id
     * @param stop_pattern
     * @param geometry
     * @param updated_at
     * @param onestop_id
     * @param color
     * @param created_at
     * @param created_or_updated_in_changeset_id
     * @param imported_from_feeds
     */
    public RouteStopPattern(List<String> identifiers, List<ImportedFromFeed> imported_from_feeds, int created_or_updated_in_changeset_id, String onestop_id, String route_onestop_id, List<String> stop_pattern, List<Double> stop_distances, RouteGeometry geometry, String color, boolean is_generated, boolean is_modified, String created_at, String updated_at, List<String> trips, Tags tags) {
        super();
        this.identifiers = identifiers;
        this.imported_from_feeds = imported_from_feeds;
        this.created_or_updated_in_changeset_id = created_or_updated_in_changeset_id;
        this.onestop_id = onestop_id;
        this.route_onestop_id = route_onestop_id;
        this.stop_pattern = stop_pattern;
        this.stop_distances = stop_distances;
        this.geometry = geometry;
        this.color = color;
        this.is_generated = is_generated;
        this.is_modified = is_modified;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.trips = trips;
        this.tags = tags;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public List<ImportedFromFeed> getImportedFromFeeds() {
        return imported_from_feeds;
    }

    public void setImportedFromFeeds(List<ImportedFromFeed> importedFromFeeds) {
        this.imported_from_feeds = importedFromFeeds;
    }

    public int getCreatedOrUpdatedInChangesetId() {
        return created_or_updated_in_changeset_id;
    }

    public void setCreatedOrUpdatedInChangesetId(int createdOrUpdatedInChangesetId) {
        this.created_or_updated_in_changeset_id = createdOrUpdatedInChangesetId;
    }

    public String getOnestopId() {
        return onestop_id;
    }

    public void setOnestopId(String onestopId) {
        this.onestop_id = onestopId;
    }

    public String getRouteOnestopId() {
        return route_onestop_id;
    }

    public void setRouteOnestopId(String routeOnestopId) {
        this.route_onestop_id = routeOnestopId;
    }

    public List<String> getStopPattern() {
        return stop_pattern;
    }

    public void setStopPattern(List<String> stopPattern) {
        this.stop_pattern = stopPattern;
    }

    public List<Double> getStopDistances() {
        return stop_distances;
    }

    public void setStopDistances(List<Double> stopDistances) {
        this.stop_distances = stopDistances;
    }

    public RouteGeometry getRouteGeometry() {
        return geometry;
    }

    public void setRouteGeometry(RouteGeometry routeGeometry) {
        this.geometry = routeGeometry;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isIsGenerated() {
        return is_generated;
    }

    public void setIsGenerated(boolean isGenerated) {
        this.is_generated = isGenerated;
    }

    public boolean isIsModified() {
        return is_modified;
    }

    public void setIsModified(boolean isModified) {
        this.is_modified = isModified;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updated_at = updatedAt;
    }

    public List<String> getTrips() {
        return trips;
    }

    public void setTrips(List<String> trips) {
        this.trips = trips;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

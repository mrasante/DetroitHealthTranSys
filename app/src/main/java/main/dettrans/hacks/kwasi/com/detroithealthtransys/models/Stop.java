package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stop {

    private List<String> identifiers = null;
    private List<ImportedFromFeed> imported_from_feeds = null;
    private Integer created_or_updated_in_changeset_id;
    private String onestop_id;
    private StopGeometry geometry;
    private String name;
    private Tags tags;
    private String timezone;
    private Integer osm_way_id;
    private List<String> served_by_vehicle_types = null;
    private Object wheelchair_boarding;
    private String created_at;
    private String updated_at;
    private List<OperatorsServingStop> operators_serving_stop = null;
    private List<RoutesServingStop> routes_serving_stop = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public List<ImportedFromFeed> getImported_from_feeds() {
        return imported_from_feeds;
    }

    public void setImported_from_feeds(List<ImportedFromFeed> imported_from_feeds) {
        this.imported_from_feeds = imported_from_feeds;
    }

    public Integer getCreatedOrUpdatedInChangesetId() {
        return created_or_updated_in_changeset_id;
    }

    public void setCreatedOrUpdatedInChangesetId(Integer createdOrUpdatedInChangesetId) {
        this.created_or_updated_in_changeset_id = createdOrUpdatedInChangesetId;
    }

    public String getOnestop_id() {
        return onestop_id;
    }

    public void setOnestop_id(String onestop_id) {
        this.onestop_id = onestop_id;
    }

    public StopGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(StopGeometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getOsmWayId() {
        return osm_way_id;
    }

    public void setOsmWayId(Integer osmWayId) {
        this.osm_way_id = osmWayId;
    }

    public List<String> getServedByVehicleTypes() {
        return served_by_vehicle_types;
    }

    public void setServedByVehicleTypes(List<String> servedByVehicleTypes) {
        this.served_by_vehicle_types = servedByVehicleTypes;
    }

    public Object getWheelchairBoarding() {
        return wheelchair_boarding;
    }

    public void setWheelchairBoarding(Object wheelchairBoarding) {
        this.wheelchair_boarding = wheelchairBoarding;
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

    public List<OperatorsServingStop> getOperatorsServingStop() {
        return operators_serving_stop;
    }

    public void setOperatorsServingStop(List<OperatorsServingStop> operatorsServingStop) {
        this.operators_serving_stop = operatorsServingStop;
    }

    public List<RoutesServingStop> getRoutesServingStop() {
        return routes_serving_stop;
    }

    public void setRoutesServingStop(List<RoutesServingStop> routesServingStop) {
        this.routes_serving_stop = routesServingStop;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
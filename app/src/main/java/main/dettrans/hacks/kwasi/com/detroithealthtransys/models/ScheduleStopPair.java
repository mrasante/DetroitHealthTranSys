
package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleStopPair {

    private String origin_onestop_id;
    private String destination_onestop_id;
    private String route_onestop_id;
    private String route_stop_pattern_onestop_id;
    private String operator_onestop_id;
    private String feed_onestop_id;
    private String feed_version_sha1;
    private String origin_timezone;
    private String destination_timezone;
    private String trip;
    private String trip_headsign;
    private Object block_id;
    private Object trip_short_name;
    private Boolean wheelchair_accessible;
    private Boolean bikes_allowed;
    private Object pickup_type;
    private Object drop_off_type;
    private Double shape_dist_traveled;
    private String origin_arrival_time;
    private String origin_departure_time;
    private String destination_arrival_time;
    private String destination_departure_time;
    private Double origin_dist_traveled;
    private Double destination_dist_traveled;
    private String service_start_date;
    private String service_end_date;
    private List<Object> service_added_dates = null;
    private List<String> service_except_dates = null;
    private List<Boolean> service_days_of_week = null;
    private String window_start;
    private String window_end;
    private String origin_timepoint_source;
    private String destination_timepoint_source;
    private Object frequency_start_time;
    private Object frequency_end_time;
    private Object frequency_headway_seconds;
    private Object frequency_type;
    private String created_at;
    private String updated_at;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ScheduleStopPair() {
    }

    /**
     * 
     * @param service_added_dates
     * @param route_stop_pattern_onestop_id
     * @param destination_onestop_id
     * @param destination_timepoint_source
     * @param destination_timezone
     * @param origin_departure_time
     * @param service_start_date
     * @param route_onestop_id
     * @param wheelchair_accessible
     * @param frequency_end_time
     * @param origin_timezone
     * @param frequency_start_time
     * @param pickup_type
     * @param destination_arrival_time
     * @param window_end
     * @param origin_onestop_id
     * @param created_at
     * @param trip
     * @param destination_dist_traveled
     * @param frequency_headway_seconds
     * @param service_end_date
     * @param feed_version_sha1
     * @param shape_dist_traveled
     * @param window_start
     * @param origin_timepoint_source
     * @param drop_off_type
     * @param trip_headsign
     * @param feed_onestop_id
     * @param frequency_type
     * @param updated_at
     * @param operator_onestop_id
     * @param origin_dist_traveled
     * @param trip_short_name
     * @param destination_departure_time
     * @param block_id
     * @param service_days_of_week
     * @param bikes_allowed
     * @param service_except_dates
     * @param origin_arrival_time
     */
    public ScheduleStopPair(String origin_onestop_id, String destination_onestop_id, String route_onestop_id, String route_stop_pattern_onestop_id, String operator_onestop_id, String feed_onestop_id, String feed_version_sha1, String origin_timezone, String destination_timezone, String trip, String trip_headsign, Object block_id, Object trip_short_name, Boolean wheelchair_accessible, Boolean bikes_allowed, Object pickup_type, Object drop_off_type, Double shape_dist_traveled, String origin_arrival_time, String origin_departure_time, String destination_arrival_time, String destination_departure_time, Double origin_dist_traveled, Double destination_dist_traveled, String service_start_date, String service_end_date, List<Object> service_added_dates, List<String> service_except_dates, List<Boolean> service_days_of_week, String window_start, String window_end, String origin_timepoint_source, String destination_timepoint_source, Object frequency_start_time, Object frequency_end_time, Object frequency_headway_seconds, Object frequency_type, String created_at, String updated_at) {
        super();
        this.origin_onestop_id = origin_onestop_id;
        this.destination_onestop_id = destination_onestop_id;
        this.route_onestop_id = route_onestop_id;
        this.route_stop_pattern_onestop_id = route_stop_pattern_onestop_id;
        this.operator_onestop_id = operator_onestop_id;
        this.feed_onestop_id = feed_onestop_id;
        this.feed_version_sha1 = feed_version_sha1;
        this.origin_timezone = origin_timezone;
        this.destination_timezone = destination_timezone;
        this.trip = trip;
        this.trip_headsign = trip_headsign;
        this.block_id = block_id;
        this.trip_short_name = trip_short_name;
        this.wheelchair_accessible = wheelchair_accessible;
        this.bikes_allowed = bikes_allowed;
        this.pickup_type = pickup_type;
        this.drop_off_type = drop_off_type;
        this.shape_dist_traveled = shape_dist_traveled;
        this.origin_arrival_time = origin_arrival_time;
        this.origin_departure_time = origin_departure_time;
        this.destination_arrival_time = destination_arrival_time;
        this.destination_departure_time = destination_departure_time;
        this.origin_dist_traveled = origin_dist_traveled;
        this.destination_dist_traveled = destination_dist_traveled;
        this.service_start_date = service_start_date;
        this.service_end_date = service_end_date;
        this.service_added_dates = service_added_dates;
        this.service_except_dates = service_except_dates;
        this.service_days_of_week = service_days_of_week;
        this.window_start = window_start;
        this.window_end = window_end;
        this.origin_timepoint_source = origin_timepoint_source;
        this.destination_timepoint_source = destination_timepoint_source;
        this.frequency_start_time = frequency_start_time;
        this.frequency_end_time = frequency_end_time;
        this.frequency_headway_seconds = frequency_headway_seconds;
        this.frequency_type = frequency_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getOriginOnestopId() {
        return origin_onestop_id;
    }

    public void setOriginOnestopId(String originOnestopId) {
        this.origin_onestop_id = originOnestopId;
    }

    public String getDestinationOnestopId() {
        return destination_onestop_id;
    }

    public void setDestinationOnestopId(String destinationOnestopId) {
        this.destination_onestop_id = destinationOnestopId;
    }

    public String getRouteOnestopId() {
        return route_onestop_id;
    }

    public void setRouteOnestopId(String routeOnestopId) {
        this.route_onestop_id = routeOnestopId;
    }

    public String getRouteStopPatternOnestopId() {
        return route_stop_pattern_onestop_id;
    }

    public void setRouteStopPatternOnestopId(String routeStopPatternOnestopId) {
        this.route_stop_pattern_onestop_id = routeStopPatternOnestopId;
    }

    public String getOperatorOnestopId() {
        return operator_onestop_id;
    }

    public void setOperatorOnestopId(String operatorOnestopId) {
        this.operator_onestop_id = operatorOnestopId;
    }

    public String getFeedOnestopId() {
        return feed_onestop_id;
    }

    public void setFeedOnestopId(String feedOnestopId) {
        this.feed_onestop_id = feedOnestopId;
    }

    public String getFeedVersionSha1() {
        return feed_version_sha1;
    }

    public void setFeedVersionSha1(String feedVersionSha1) {
        this.feed_version_sha1 = feedVersionSha1;
    }

    public String getOriginTimezone() {
        return origin_timezone;
    }

    public void setOriginTimezone(String originTimezone) {
        this.origin_timezone = originTimezone;
    }

    public String getDestinationTimezone() {
        return destination_timezone;
    }

    public void setDestinationTimezone(String destinationTimezone) {
        this.destination_timezone = destinationTimezone;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getTripHeadsign() {
        return trip_headsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.trip_headsign = tripHeadsign;
    }

    public Object getBlockId() {
        return block_id;
    }

    public void setBlockId(Object blockId) {
        this.block_id = blockId;
    }

    public Object getTripShortName() {
        return trip_short_name;
    }

    public void setTripShortName(Object tripShortName) {
        this.trip_short_name = tripShortName;
    }

    public Boolean getWheelchairAccessible() {
        return wheelchair_accessible;
    }

    public void setWheelchairAccessible(Boolean wheelchairAccessible) {
        this.wheelchair_accessible = wheelchairAccessible;
    }

    public Boolean getBikesAllowed() {
        return bikes_allowed;
    }

    public void setBikesAllowed(Boolean bikesAllowed) {
        this.bikes_allowed = bikesAllowed;
    }

    public Object getPickupType() {
        return pickup_type;
    }

    public void setPickupType(Object pickupType) {
        this.pickup_type = pickupType;
    }

    public Object getDropOffType() {
        return drop_off_type;
    }

    public void setDropOffType(Object dropOffType) {
        this.drop_off_type = dropOffType;
    }

    public Double getShapeDistTraveled() {
        return shape_dist_traveled;
    }

    public void setShapeDistTraveled(Double shapeDistTraveled) {
        this.shape_dist_traveled = shapeDistTraveled;
    }

    public String getOriginArrivalTime() {
        return origin_arrival_time;
    }

    public void setOriginArrivalTime(String originArrivalTime) {
        this.origin_arrival_time = originArrivalTime;
    }

    public String getOriginDepartureTime() {
        return origin_departure_time;
    }

    public void setOriginDepartureTime(String originDepartureTime) {
        this.origin_departure_time = originDepartureTime;
    }

    public String getDestinationArrivalTime() {
        return destination_arrival_time;
    }

    public void setDestinationArrivalTime(String destinationArrivalTime) {
        this.destination_arrival_time = destinationArrivalTime;
    }

    public String getDestinationDepartureTime() {
        return destination_departure_time;
    }

    public void setDestinationDepartureTime(String destinationDepartureTime) {
        this.destination_departure_time = destinationDepartureTime;
    }

    public Double getOriginDistTraveled() {
        return origin_dist_traveled;
    }

    public void setOriginDistTraveled(Double originDistTraveled) {
        this.origin_dist_traveled = originDistTraveled;
    }

    public Double getDestinationDistTraveled() {
        return destination_dist_traveled;
    }

    public void setDestinationDistTraveled(Double destinationDistTraveled) {
        this.destination_dist_traveled = destinationDistTraveled;
    }

    public String getServiceStartDate() {
        return service_start_date;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.service_start_date = serviceStartDate;
    }

    public String getServiceEndDate() {
        return service_end_date;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.service_end_date = serviceEndDate;
    }

    public List<Object> getServiceAddedDates() {
        return service_added_dates;
    }

    public void setServiceAddedDates(List<Object> serviceAddedDates) {
        this.service_added_dates = serviceAddedDates;
    }

    public List<String> getServiceExceptDates() {
        return service_except_dates;
    }

    public void setServiceExceptDates(List<String> serviceExceptDates) {
        this.service_except_dates = serviceExceptDates;
    }

    public List<Boolean> getServiceDaysOfWeek() {
        return service_days_of_week;
    }

    public void setServiceDaysOfWeek(List<Boolean> serviceDaysOfWeek) {
        this.service_days_of_week = serviceDaysOfWeek;
    }

    public String getWindowStart() {
        return window_start;
    }

    public void setWindowStart(String windowStart) {
        this.window_start = windowStart;
    }

    public String getWindowEnd() {
        return window_end;
    }

    public void setWindowEnd(String windowEnd) {
        this.window_end = windowEnd;
    }

    public String getOriginTimepointSource() {
        return origin_timepoint_source;
    }

    public void setOriginTimepointSource(String originTimepointSource) {
        this.origin_timepoint_source = originTimepointSource;
    }

    public String getDestinationTimepointSource() {
        return destination_timepoint_source;
    }

    public void setDestinationTimepointSource(String destinationTimepointSource) {
        this.destination_timepoint_source = destinationTimepointSource;
    }

    public Object getFrequencyStartTime() {
        return frequency_start_time;
    }

    public void setFrequencyStartTime(Object frequencyStartTime) {
        this.frequency_start_time = frequencyStartTime;
    }

    public Object getFrequencyEndTime() {
        return frequency_end_time;
    }

    public void setFrequencyEndTime(Object frequencyEndTime) {
        this.frequency_end_time = frequencyEndTime;
    }

    public Object getFrequencyHeadwaySeconds() {
        return frequency_headway_seconds;
    }

    public void setFrequencyHeadwaySeconds(Object frequencyHeadwaySeconds) {
        this.frequency_headway_seconds = frequencyHeadwaySeconds;
    }

    public Object getFrequencyType() {
        return frequency_type;
    }

    public void setFrequencyType(Object frequencyType) {
        this.frequency_type = frequencyType;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

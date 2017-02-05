package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

/**
 * Created by kwas7493 on 1/26/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class Meta {


    private String sortKey;
    private String sortOrder;
    private Integer offset;
    private Integer perPage;
    private String next;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Meta() {
    }

    /**
     *
     * @param sortOrder
     * @param next
     * @param perPage
     * @param offset
     * @param sortKey
     */
    public Meta(String sortKey, String sortOrder, Integer offset, Integer perPage, String next) {
        super();
        this.sortKey = sortKey;
        this.sortOrder = sortOrder;
        this.offset = offset;
        this.perPage = perPage;
        this.next = next;
    }



    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

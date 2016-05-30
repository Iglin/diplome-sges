package ru.ssk.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 30.05.2016.
 */
public class FiltersMap {
    private Map<String, Map<String, String>> filters = new HashMap<>();

    public FiltersMap() {
    }

    public Map<String, Map<String, String>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Map<String, String>> filters) {
        this.filters = filters;
    }

    public void putFilter(String parameter, Map<String, String> values) {
        filters.put(parameter, values);
    }

    public void removeFilter(String parameter) {
        filters.remove(parameter);
    }

    public Map<String, String> getFilterValues(String parameter) {
        return filters.get(parameter);
    }
}

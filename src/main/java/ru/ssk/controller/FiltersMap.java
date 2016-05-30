package ru.ssk.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 30.05.2016.
 */
public class FiltersMap {
    private Map<String, String> filters = new HashMap<>();

    public FiltersMap() {
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public void putFilter(String parameter, String value) {
        filters.put(parameter, value);
    }

    public void removeFilter(String parameter) {
        filters.remove(parameter);
    }

    public String getFiltersValue(String parameter) {
        return filters.get(parameter);
    }
}

package com.albertomadrazo.android.gitlook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lenguaje {
    @SerializedName("total_count")
    @Expose
    private String totalCount;
    @SerializedName("items")
    @Expose
    private List<Repositorio> items = null;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<Repositorio> getItems() {
        return items;
    }

    public void setItems(List<Repositorio> items) {
        this.items = items;
    }
}

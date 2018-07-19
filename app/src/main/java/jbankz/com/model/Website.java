package jbankz.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Website {
    @SerializedName("status")
    private String status;
    private List<Source> sources;

    public Website() {
    }

    public Website(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}

package com.tsystem.crawler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrawlDetail implements Serializable {

    private Integer total_links;
    private Integer total_images;
    private List<PageDetail> details;

    public Integer getTotal_links() {
        return total_links;
    }

    public void setTotal_links(Integer total_links) {
        this.total_links = total_links;
    }

    public Integer getTotal_images() {
        return total_images;
    }

    public void setTotal_images(Integer total_images) {
        this.total_images = total_images;
    }

    public List<PageDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PageDetail> details) {
        this.details = details;
    }
}

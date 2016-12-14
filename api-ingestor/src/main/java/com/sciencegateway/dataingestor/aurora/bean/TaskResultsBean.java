package com.sciencegateway.dataingestor.aurora.bean;

import com.sciencegateway.dataingestor.aurora.client.sdk.ScheduleStatus;

import java.net.URI;
import java.util.List;

/**
 * Created by janakbhalla on 11/12/16.
 */
public class TaskResultsBean {

    private ScheduleStatus scheduleStatus;

    private List<URI> imageUrls;

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public List<URI> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<URI> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public TaskResultsBean(ScheduleStatus scheduleStatus, List<URI> imageUrls) {
        this.scheduleStatus = scheduleStatus;
        this.imageUrls = imageUrls;
    }
}

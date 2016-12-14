package com.sciencegateway.dataingestor.aurora.bean;

import java.util.List;

/**
 * Created by janakbhalla on 10/12/16.
 */
public class JobResultsBean {

    private String jobName;

    private List<TaskResultsBean> taskResultsBeans;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobResultsBean(String jobName, List<TaskResultsBean> taskResultsBeans) {
        this.jobName = jobName;
        this.taskResultsBeans = taskResultsBeans;
    }

    public List<TaskResultsBean> getTaskResultsBeans() {
        return taskResultsBeans;
    }

    public void setTaskResultsBeans(List<TaskResultsBean> taskResultsBeans) {
        this.taskResultsBeans = taskResultsBeans;
    }
}

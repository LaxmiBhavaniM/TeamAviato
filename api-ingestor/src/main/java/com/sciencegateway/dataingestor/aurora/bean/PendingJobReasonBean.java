package com.sciencegateway.dataingestor.aurora.bean;

import com.sciencegateway.dataingestor.aurora.client.sdk.PendingReason;

import java.util.Set;

public class PendingJobReasonBean extends ResponseBean {

    private Set<PendingReason> reasons;

    /**
     * Instantiates a new pending job response bean.
     *
     * @param responseBean the response bean
     */
    public PendingJobReasonBean(ResponseBean responseBean) {
        this.setResponseCode(responseBean.getResponseCode());
        this.setServerInfo(responseBean.getServerInfo());
    }

    /**
     * Gets the reasons.
     *
     * @return the reasons
     */
    public Set<PendingReason> getReasons() {
        return reasons;
    }

    /**
     * Sets the reasons.
     *
     * @param reasons the new reasons
     */
    public void setReasons(Set<PendingReason> reasons) {
        this.reasons = reasons;
    }

    /* (non-Javadoc)
     * @see org.apache.airavata.cloud.aurora.client.bean.ResponseBean#toString()
     */
    @Override
    public String toString() {
        return "PendingJobResponseBean [reasons=" + reasons + "]";
    }

}

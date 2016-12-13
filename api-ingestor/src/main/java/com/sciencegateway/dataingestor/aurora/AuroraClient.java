package com.sciencegateway.dataingestor.aurora;

import com.sciencegateway.dataingestor.aurora.bean.*;
import com.sciencegateway.dataingestor.aurora.client.AuroraThriftClient;
import com.sciencegateway.dataingestor.aurora.client.sdk.ScheduleStatus;
import com.sciencegateway.dataingestor.aurora.client.sdk.ScheduledTask;
import com.sciencegateway.dataingestor.aurora.utils.AuroraThriftClientUtil;
import com.sciencegateway.dataingestor.aurora.utils.Constants;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AuroraClient {

    final static Logger logger = Logger.getLogger(AuroraClient.class);

    public static JobDetailsResponseBean getJobDetails(String jobName) throws Exception {
        JobKeyBean jobKey = new JobKeyBean("devel", "team-aviato", jobName);
        return (JobDetailsResponseBean) AuroraThriftClient.getAuroraThriftClient(Constants.AURORA_SCHEDULER_PROP_FILE).getJobDetails(jobKey);
    }

    public static ResponseBean createJob(String jobName) throws Exception {

        JobKeyBean jobKey = new JobKeyBean("devel", "team-aviato", jobName);
        IdentityBean owner = new IdentityBean("team-aviato");
        ProcessBean proc1 = new ProcessBean("process_1", "docker run -i --volumes-from wpsgeog --volumes-from wrfinputsandy -v ~/wrfoutput:/wrfoutput --name " + jobName + "_pc1 bigwxwrf/ncar-wrf /wrf/run-wrf", false);
        ProcessBean proc2 = new ProcessBean("process_2", "docker run -i --rm=true -v ~/wrfoutput:/wrfoutput --name " + jobName + "_pc2 bigwxwrf/ncar-ncl", false);
        ProcessBean proc0 = new ProcessBean("process_0", "docker rm -f " + jobName + "_pc1; docker rm -f " + jobName + "_pc2;echo 'remove containers'", false);
        Set<ProcessBean> processes = new LinkedHashSet<>();
        processes.add(proc1);
        processes.add(proc2);
        processes.add(proc0);

        ResourceBean resources = new ResourceBean(0.2, 400, 400);

        TaskConfigBean taskConfig = new TaskConfigBean("data_ingestor_task", processes, resources);
        JobConfigBean jobConfig = new JobConfigBean(jobKey, owner, taskConfig, "example");

        String executorConfigJson = AuroraThriftClientUtil.getExecutorConfigJson(jobConfig);
        logger.info(executorConfigJson);

        AuroraThriftClient client = AuroraThriftClient.getAuroraThriftClient(Constants.AURORA_SCHEDULER_PROP_FILE);
        return client.createJob(jobConfig);

    }

    public static List<JobResultsBean> getJobDetailsList(List<String> jobNames) throws Exception {
        List<JobResultsBean> jobResultsBeanList = new ArrayList<>();
        for (String jobName : jobNames) {
            JobDetailsResponseBean jobDetailsResponseBean = getJobDetails(jobName);
            List<TaskResultsBean> taskResultsBeanList = new ArrayList<>();
            for(ScheduledTask scheduledTask : jobDetailsResponseBean.getTasks()) {
                List<URI> urlList = new ArrayList<>();
                String hostAddr = null;
                if (scheduledTask.getStatus() == ScheduleStatus.FINISHED) {
                    String hostSlaveName = scheduledTask.getAssignedTask().getSlaveHost();
                    if (hostSlaveName.equals("sga-mesos-slave-1"))
                        hostAddr = "http://52.53.179.0:1338/download/";
                    else
                        hostAddr = "http://54.215.219.32:1338/download/";
                    urlList.add(new URI(hostAddr + scheduledTask.getAssignedTask().getTaskId() + "/wrfoutput/Precip_total.gif"));
                    urlList.add(new URI(hostAddr + scheduledTask.getAssignedTask().getTaskId() + "/wrfoutput/Surface_multi.gif"));
                }
                TaskResultsBean taskResultsBean = new TaskResultsBean(scheduledTask.getStatus(), urlList);
                taskResultsBeanList.add(taskResultsBean);
            }
            JobResultsBean jobResultsBean = new JobResultsBean(jobName, taskResultsBeanList);
            jobResultsBeanList.add(jobResultsBean);
        }
        return jobResultsBeanList;
    }
}

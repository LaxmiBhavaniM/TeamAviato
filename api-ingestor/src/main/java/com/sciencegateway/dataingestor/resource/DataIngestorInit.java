package com.sciencegateway.dataingestor.resource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class DataIngestorInit implements ServletContextListener
{
	
	private static Logger logger = Logger.getLogger(DataIngestorResource.class);
	
	private ServletContext context = null;

    @Override
    public void contextDestroyed(ServletContextEvent event) 
    {
        this.context = null;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) 
    {
        this.context = event.getServletContext();
        logger.info("Registering Service...");
    	String serviceURI = "http://ec2-35-160-243-251.us-west-2.compute.amazonaws.com:9000/dataingestor/webapi/service/url";
    	String serviceName = "dataIngestor";
    	int port = 9000;
    	
    	CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("ec2-35-160-243-251.us-west-2.compute.amazonaws.com:2181", new RetryNTimes(5, 1000));
        curatorFramework.start();
        
        try 
        {            
            @SuppressWarnings("rawtypes")
			ServiceInstance serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(serviceURI)).address("ec2-35-160-243-251.us-west-2.compute.amazonaws.com").port(port)
					.name(serviceName).build();
            ServiceDiscoveryBuilder.builder(Void.class).basePath("weather-predictor").client(curatorFramework)
			.thisInstance(serviceInstance).build().start();
        } 
        catch (Exception exception) 
        {
        	logger.error(exception.toString(),exception);
            throw new RuntimeException("Could not register service \"" + serviceName + "\", with URI \"" + serviceURI + "\": " + exception.getMessage());
        }   
    }

}
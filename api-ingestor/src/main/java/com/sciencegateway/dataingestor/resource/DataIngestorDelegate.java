package com.sciencegateway.dataingestor.resource;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.log4j.Logger;

@Path("/ingestor")
public class DataIngestorDelegate 
{

	private static Logger logger = Logger.getLogger(DataIngestorDelegate.class);

	@GET
	@Path("/try")
	@Produces(MediaType.TEXT_PLAIN)
	public String generateString() throws IOException
	{
		return "Got it!";
	}
	
	@SuppressWarnings("unused")
	@GET
	@Path("/delegate")
	@Produces(MediaType.TEXT_PLAIN)
	public String delegate() 
	{
		logger.info("Inside Delegator...");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("ec2-35-160-243-251.us-west-2.compute.amazonaws.com:2181", new RetryNTimes(5, 1000));
		curatorFramework.start();
		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class).basePath("weather-predictor").client(curatorFramework).build();
		
		try 
		{
			serviceDiscovery.start();
		} 
		catch (Exception exception) 
		{
			logger.error(exception.toString(),exception);
			exception.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("dataIngestor")
				.build();
		try 
		{
			serviceProvider.start();
		} 
		catch (Exception exception) 
		{
			logger.error(exception.toString(),exception);
			exception.printStackTrace();
		}
		
		@SuppressWarnings("rawtypes")
		ServiceInstance instance;		
		
		try 
		{
			List<ServiceInstance<Void>> instances =(List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if ( instances.size() == 0 )
	        {
	            return null;
	        }
			
	        int thisIndex = DataIngestorService.getIndex();
	        DataIngestorService.setIndex(thisIndex+1);
	        System.out.println("thisIndex: " + thisIndex);
	        System.out.println(instances.get(thisIndex % instances.size()));
	        	       
			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec uriSpec = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = uriSpec.build();
			System.out.println("URL: " + url);
			System.out.println("Address: " + address);
			return url;
			
		} 
		catch (Exception exception) 
		{
			logger.error(exception.toString(),exception);
			exception.printStackTrace();
		}
		
	    return "Delegated!";	    
	}
	
}

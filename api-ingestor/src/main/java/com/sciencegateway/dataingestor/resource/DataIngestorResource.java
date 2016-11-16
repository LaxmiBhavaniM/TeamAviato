package com.sciencegateway.dataingestor.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.UriSpec;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;

import com.sciencegateway.dataingestor.POJO.URLObjects;
import com.sciencegateway.dataingestor.service.URLConverter;

@Path("/service")
public class DataIngestorResource 
{	
	private URLConverter urlConverter = new URLConverter();
	
	@GET
	@Path("/try")
	@Produces(MediaType.TEXT_PLAIN)
	public String generateString() throws IOException
	{
		return "Got it!";
	}
	
	@SuppressWarnings("unchecked")
	@GET
    @Path("/init")
    @Produces("text/plain")
    public String registerService() throws IOException 
    {
		System.out.println("Registering Service...");
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
            throw new RuntimeException("Could not register service \"" + serviceName + "\", with URI \"" + serviceURI + "\": " + exception.getMessage());
        }
        
        return "Service Registered!";
        
    }
	
	@SuppressWarnings("unused")
	@GET
	@Path("/delegate")
	@Produces("text/plain")
	public String delegate() 
	{
		System.out.println("Inside Delegator...");
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("ec2-35-160-243-251.us-west-2.compute.amazonaws.com:2181", new RetryNTimes(5, 1000));
		curatorFramework.start();
		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class).basePath("weather-predictor").client(curatorFramework).build();
		
		try 
		{
			serviceDiscovery.start();
		} 
		catch (Exception exception) 
		{
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
			exception.printStackTrace();
		}
		
	    return "Delegated!";	    
	}
	
	@POST
	@Path("/url")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateURL(URLObjects urlObjects)	
	{
		System.out.println("Receiving data from UI...");
		System.out.println(urlObjects);
		System.out.println("Sending URL to Storm Detector...");
		JSONObject jsonObject = new JSONObject();
		try 
		{
			urlObjects = urlConverter.getURL(urlObjects);
			System.out.println(urlObjects);
			jsonObject.put("requestId", urlObjects.getRequestId());
			jsonObject.put("userName", urlObjects.getUserName());
			jsonObject.put("url", urlObjects.getUrl());	
			System.out.println(jsonObject.toString());
			
			generateLOG(urlObjects);
			return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
		} 
		catch (MalformedURLException | ParseException exception)
		{
			exception.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}		
		catch (Exception exception)
		{
			return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
		}
	}
	
	public int generateLOG(URLObjects urlObjects) throws Exception
	{
		System.out.println("Sending LOG to Registry...");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requestId", urlObjects.getRequestId());
		jsonObject.put("userName", urlObjects.getUserName());
		jsonObject.put("serviceName", "Data Ingestor");
		jsonObject.put("description", urlObjects.getUrl());
		System.out.println(jsonObject.toString());
		ClientConfig clientConfigR = new ClientConfig();
		Client clientR = ClientBuilder.newClient(clientConfigR);
		clientR.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		WebTarget targetR = clientR.target("http://ec2-35-160-243-251.us-west-2.compute.amazonaws.com:8082/registry/v1/service/log");
		System.out.println(targetR.toString());
		Response responseToR = targetR.request().post(Entity.entity(jsonObject.toString(), "application/json"),Response.class);
		System.out.println(responseToR.toString());
		return responseToR.getStatus();
	}
	
}

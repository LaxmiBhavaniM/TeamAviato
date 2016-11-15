package com.sciencegateway.dataingestor.resource;

import java.net.MalformedURLException;
import java.text.ParseException;

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

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;

import com.sciencegateway.dataingestor.POJO.URLObjects;
import com.sciencegateway.dataingestor.service.URLConverter;

@Path("/service")
public class RESTResource 
{
	URLConverter urlConverter = new URLConverter();
	URLObjects urlObjects = new URLObjects();
	
	@GET
	@Path("/try")
	@Produces(MediaType.TEXT_PLAIN)
	public String generateString()
	{
		return "Got it!";
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

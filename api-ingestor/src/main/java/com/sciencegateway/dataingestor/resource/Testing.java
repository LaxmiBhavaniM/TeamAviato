package com.sciencegateway.dataingestor.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;

//This is just a test class to test the init and delegate functionalities of Zookeeper

@Path("/req")
public class Testing 
{

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test()
	{
		ClientConfig clientConfigR = new ClientConfig();
		Client clientR = ClientBuilder.newClient(clientConfigR);
		clientR.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		WebTarget targetR = clientR.target("http://ec2-35-160-243-251.us-west-2.compute.amazonaws.com:9000/dataingestor/webapi/ingestor/delegate");
		String url = targetR.request().get(String.class);
		System.out.println(url);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stationName", "KIND");
		jsonObject.put("date", "2000-08-28");
		jsonObject.put("time", "123456");
		jsonObject.put("requestId", "1001");
		jsonObject.put("userName", "Sneha");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		clientR.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		WebTarget target = client.target("http://ec2-35-160-243-251.us-west-2.compute.amazonaws.com:9000/dataingestor/webapi/service/url");
		return target.request().post(Entity.entity(jsonObject.toString(), "application/json"),Response.class);
	}
	
}

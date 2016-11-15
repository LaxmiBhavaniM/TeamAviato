package org.aviato.registry.resource;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.aviato.registry.POJO.RegistryPOJO;
import org.aviato.registry.service.RegistryServices;
import org.json.JSONArray;

@Path("/service")
public class RegistryResource {	
	
	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
	    return "Got it!";
	}	
	
	RegistryServices registryServices = new RegistryServices();	
	
	/*Function to retrieve user logs when requested by UI*/
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)		
	public Response getAllLogs(RegistryPOJO log)
	{		
		Connection conn;
		try {
			conn = registryServices.makeConnection();
			JSONArray resultJson = registryServices.getUserLog(conn, log);
			registryServices.closeConnection(conn);
			return Response.ok(resultJson.toString(), MediaType.APPLICATION_JSON).build();
		}
		catch (Exception exception) {
			exception.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}			
	}
	
	/*Function to write micro-service log data into registry*/
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addLog(RegistryPOJO log) throws Exception{

		Connection conn = registryServices.makeConnection();
		registryServices.addLogToRegistry(conn, log);
		registryServices.closeConnection(conn);
		return "Success!";
	}
}
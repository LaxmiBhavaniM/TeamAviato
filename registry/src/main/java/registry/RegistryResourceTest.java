package registry;

import static org.junit.Assert.*;

import org.junit.Test;

import org.aviato.registry.POJO.RegistryPOJO;
import org.aviato.registry.resource.RegistryResource;
import org.junit.Before;

public class RegistryResourceTest {
	
	private RegistryResource registryResource;
	private RegistryPOJO log;
	
	@Before
	public void setUp() throws Exception
	{
		registryResource = new RegistryResource();
		log = new RegistryPOJO();
	}

	
	@Test
	public void getAllLogsTest() {
		
		log.setUserName("TestUser");
		assertEquals(200, registryResource.getAllLogs(log).getStatus());
		
	}
	
	
	@Test
	public void addLogTest() throws Exception{
		
		log.setDescription("TestDescription");
		log.setRequestId("TestRequestId");
		log.setServiceName("TestServiceName");
		log.setUserName("TestUser");		
		
		assertEquals("Success!",registryResource.addLog(log));
		
	}
}

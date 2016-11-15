package registry;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.aviato.registry.POJO.RegistryPOJO;
import org.aviato.registry.service.RegistryServices;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;

public class RegistryServiceTest {
	
	private RegistryServices registryService;
	private RegistryPOJO log;
	private Connection conn;
	
	
	@Before
	public void setUp() throws Exception
	{
		registryService = new RegistryServices();	
		log = new RegistryPOJO();
		conn = (Connection) registryService.makeConnection();
		
	}

	
	@Test
	public void makeConnectionTest() throws SQLException {				
		
		if(conn == null){
			assertEquals(null, conn);
			System.out.println("Cannot connect to registry");
		}
		else{
			assertTrue(conn.isValid(0));
		}		
	}
	
	@Test
	public void getDataLogTest() throws Exception{
		log.setUserName("TestUser");
		assertNotNull(registryService.getUserLog(conn, log));
	}

}

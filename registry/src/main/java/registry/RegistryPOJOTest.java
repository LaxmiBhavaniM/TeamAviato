package registry;

import org.aviato.registry.POJO.RegistryPOJO;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class RegistryPOJOTest {

	@Test
	public void testPOJO() throws Exception{
		new BeanTester().testBean(RegistryPOJO.class);
	}

}

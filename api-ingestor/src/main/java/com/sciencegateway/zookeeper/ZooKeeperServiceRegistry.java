package com.sciencegateway.zookeeper;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

@ZooKeeperServices
@ApplicationScoped
public class ZooKeeperServiceRegistry implements ServiceRegistry 
{

    private CuratorFramework curatorFramework;
    @SuppressWarnings("rawtypes")
	private ServiceInstance serviceInstance = null;
    
    public CuratorFramework getCuratorFramework() 
    {
		return curatorFramework;
	}

    @SuppressWarnings("unchecked")
	@Override
    public void registerService(String name, String uri) 
    {
    	int port = 8080;
    	
    	CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(5, 1000));
        curatorFramework.start();
    	
        try 
        {            
            serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(uri)).address("localhost").port(port)
					.name(name).build();
            ServiceDiscoveryBuilder.builder(Void.class).basePath("weather-predictor").client(curatorFramework)
			.thisInstance(serviceInstance).build().start();
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException("Could not register service \"" 
                    + name 
                    + "\", with URI \"" + uri + "\": " + ex.getMessage());
        }
    }
    
    @Override
    public void unregisterService(String name, String uri) 
    {
        try 
        {
                curatorFramework.delete().forPath(uri);
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException("Could not unregister service \"" 
                    + name 
                    + "\", with URI \"" + uri + "\": " + ex.getMessage());
        }
    }

    @Override
    public String discoverServiceURI(String name) 
    {
        try 
        {
            String znode = "/services/" + name;

            List<String> uris = curatorFramework.getChildren().forPath(znode);
            return new String(curatorFramework.getData().forPath(ZKPaths.makePath(znode, uris.get(0))));
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException("Service \"" + name + "\" not found: " + ex.getMessage());
        }
    }
}
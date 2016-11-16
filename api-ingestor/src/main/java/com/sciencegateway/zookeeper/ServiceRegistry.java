package com.sciencegateway.zookeeper;

public interface ServiceRegistry 
{
	
    public void registerService(String name, String uri);  
    public void unregisterService(String name, String uri);    
    public String discoverServiceURI(String name);
    
}
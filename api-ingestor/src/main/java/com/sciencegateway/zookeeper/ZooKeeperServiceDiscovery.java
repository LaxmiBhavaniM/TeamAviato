package com.sciencegateway.zookeeper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ZooKeeperServices
@ApplicationScoped
public class ZooKeeperServiceDiscovery extends ServiceDiscovery 
{

    @Inject
    @ZooKeeperServices
    ServiceRegistry services;

    @Override
    public String getUserServiceURI() 
    {
        return services.discoverServiceURI("user");
    }

    @Override
    public String getCatalogServiceURI() 
    {
        return services.discoverServiceURI("catalog");
    }

    @Override
    public String getOrderServiceURI() 
    {
        return services.discoverServiceURI("order");
    }
}
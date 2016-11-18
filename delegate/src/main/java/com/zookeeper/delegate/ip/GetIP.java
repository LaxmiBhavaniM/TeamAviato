package com.zookeeper.delegate.ip;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.zookeeper.delegate.clustering.DelegateStormClustering;

public class GetIP 
{

	private static Logger logger = Logger.getLogger(DelegateStormClustering.class);
	
	public static String getValidIP() throws UnknownHostException, IOException
	{
		int timeOut = 1000;
		String ip1 = IP.ipAddress1;
		String ip2 = IP.ipAddress2;
		String ip3 = IP.ipAddress3;
		
		if (InetAddress.getByName(ip1).isReachable(timeOut))
		{
			logger.info(ip1 + " is reachable");
			return ip1;
		}
		else if (InetAddress.getByName(ip2).isReachable(timeOut))
		{
			logger.info(ip2 + " is reachable");
			return ip2;
		}
		else if (InetAddress.getByName(ip3).isReachable(timeOut))
		{
			logger.info(ip3 + " is reachable");
			return ip3;
		}
		logger.error("Zookeeper service not reachable!");
		return null;
	}
	
}
package com.tianyalan.eurekaserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryService {

	@Autowired
	private DiscoveryClient discoveryClient;

	public List<String> getEurekaServices() {
		List<String> services = new ArrayList<String>();

		discoveryClient.getServices().forEach(serviceName -> {
			discoveryClient.getInstances(serviceName).forEach(instance -> {
				services.add(String.format("%s:%s", serviceName, instance.getUri()));
			});
		});

		return services;
	}

}

package mx.interware.scdemo.talker.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;

public class HelloConfiguration {

	@Autowired
	IClientConfig ribbonClientConfig;

	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new AvailabilityFilteringRule();
	}

}
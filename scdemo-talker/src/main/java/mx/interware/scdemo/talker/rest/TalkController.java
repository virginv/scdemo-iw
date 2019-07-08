package mx.interware.scdemo.talker.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RibbonClient(name = "scdemo-greeter")
public class TalkController {
	private static final Logger log = LogManager.getLogger(TalkController.class);

	private String[] names = { "Sofia", "Angel", "Olivia" };

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@HystrixCommand(fallbackMethod = "greeterFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public String invokeGreeter(String name) {
		String response = new RestTemplate().getForObject("http://scdemo-greeter/greetings/" + name, String.class);
		return response;
	}

	@HystrixCommand
	private String greeterFallback(String name) {
		return "Can't say hello at this moment " + name + " ;)";
	}

	@RequestMapping(value = "/just-talk", method = RequestMethod.GET)
	public ResponseEntity<?> justTalk() {
		String name = names[(int) (Math.random() * names.length)];
		log.info("Talking using name: " + name + " ...");
		String response = this.restTemplate.getForObject("http://scdemo-greeter/greetings/" + name, String.class);
		log.info("response: " + response);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/protected-talk", method = RequestMethod.GET)
	public ResponseEntity<?> protectedTalk() {
		String name = names[(int) (Math.random() * names.length)];
		log.info("Protected talking using name: " + name + " ...");
		String response = invokeGreeter(name);
		log.info("response: " + response);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		return "There’s nothing here, yet.";
	}

}

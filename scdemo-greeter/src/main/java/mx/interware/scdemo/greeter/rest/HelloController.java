package mx.interware.scdemo.greeter.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.interware.scdemo.greeter.config.Configuration;

@RestController
public class HelloController {
	private static final Logger log = LogManager.getLogger(HelloController.class);
	
	@Value("${minimum}")
    private String minimun;
	
	@Value("${maximum}")
    private String maximum;
	
	@Value("${pass}")
    private String pass;
	
	@Autowired
	private Configuration configuration;
	
	@Autowired
	private Environment env;

	@RequestMapping(value = "/greetings/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> greetings(@PathVariable(value = "name") String name) throws InterruptedException {
		String message = "Greetings from Spring Cloud " + env.getProperty("local.server.port") + ": " + name + "!";
		log.info("minimun: " + minimun + " maximum: " + maximum);
		log.info("pass: " + pass + " configuration.getPass: " + configuration.getPass());
		log.info("Configuration.getMinimum: " + configuration.getMinimum() + " Configuration.getMaximum: " + configuration.getMaximum());
		log.info("Port: " + env.getProperty("local.server.port"));
		log.info("Saying hello: " + message + " ...");
		//Thread.sleep(60000);
		return new ResponseEntity(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		log.info("Pinging ...");
		return "pong";
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		return "There’s nothing here, yet.";
	}

}

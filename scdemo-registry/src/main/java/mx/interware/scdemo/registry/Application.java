package mx.interware.scdemo.registry;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Application {
	private static final Logger log = LogManager.getLogger(Application.class);

	private static String buildSignature() {
		String signature = "SCDEMO-REGISTRY\n";
		return signature;
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		String serviceName = env.get("SERVICE_NAME");
		String serviceVersion = env.get("SERVICE_VERSION");
		if (serviceName == null)
			serviceName = "scdemo-registry";
		if (serviceVersion == null)
			serviceVersion = "-";

		log.info("################ Starting service : " + serviceName + " v" + serviceVersion);
		log.info(buildSignature());
		SpringApplication.run(Application.class, args);
	}

}

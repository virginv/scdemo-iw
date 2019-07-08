package mx.interware.scdemo.gateway;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIGatewayApplication {

	private static final Logger log = LogManager.getLogger(APIGatewayApplication.class);

	private static String buildSignature() {
		String signature = "SCDEMO-API\n";
		return signature;
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		String serviceName = env.get("SERVICE_NAME");
		String serviceVersion = env.get("SERVICE_VERSION");
		if (serviceName == null)
			serviceName = "scdemo-api";
		if (serviceVersion == null)
			serviceVersion = "-";

		log.info("################ Starting service : " + serviceName + " v" + serviceVersion);
		log.info(buildSignature());
		SpringApplication.run(APIGatewayApplication.class, args);
	}

}
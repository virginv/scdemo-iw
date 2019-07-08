package mx.interware.scdemo.talker;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableAsync
@EnableSwagger2
public class TalkerApplication {
	private static final Logger log = LogManager.getLogger(TalkerApplication.class);

	private static String buildSignature() {
		String signature = "SCDEMO-TALKER\n";
		return signature;
	}

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();
		String serviceName = env.get("SERVICE_NAME");
		String serviceVersion = env.get("SERVICE_VERSION");
		if (serviceName == null)
			serviceName = "scdemo-talker";
		if (serviceVersion == null)
			serviceVersion = "-";

		log.info("################ Starting service : " + serviceName + " v" + serviceVersion);
		log.info(buildSignature());
		SpringApplication.run(TalkerApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("mx.interware.scdemo.talker")).paths(PathSelectors.any())
				.build();
	}
}

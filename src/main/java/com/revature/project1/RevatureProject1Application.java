package com.revature.project1;

import com.revature.project1.annotations.Author;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Author(authorName = "Leo Schaffner",
		description = "Application Start and Custom Metrics")
@SpringBootApplication
@EnableAspectJAutoProxy
public class RevatureProject1Application {
	public static void main(String[] args) { SpringApplication.run(RevatureProject1Application.class, args); }

	@Bean // Creates timer metric, added to all controller methods
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}
}

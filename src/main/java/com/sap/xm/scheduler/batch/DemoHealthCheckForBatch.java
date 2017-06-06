package com.sap.xm.scheduler.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DemoHealthCheckForBatch implements HealthIndicator {

	@Autowired
	JobCompletionNotificationListener jobCompletionListener;

	@Override
	public Health health() {
		if (jobCompletionListener.isRunning()) {
			return Health.up().build();
		} else {
			return Health.down().withDetail("Error Code", "Failed").build();
		}
	}

}

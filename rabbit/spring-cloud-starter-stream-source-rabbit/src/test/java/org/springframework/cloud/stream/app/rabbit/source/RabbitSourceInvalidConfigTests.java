/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.app.rabbit.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.fail;

import org.junit.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Tests for RabbitSource with invalid config.
 *
 * @author Gary Russell
 */
public class RabbitSourceInvalidConfigTests {

	@Test
	public void testNoQueues() throws Exception {
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			EnvironmentTestUtils.addEnvironment(context, "rabbit.enableRetry:false");
			context.register(Config.class);
			context.refresh();
			fail("BeanCreationException expected");
		}
		catch (Exception e) {
			assertThat(e, instanceOf(BeanCreationException.class));
			assertThat(e.getMessage(), containsString("queue(s) are required"));
		}
	}

	@Test
	public void testEmptyQueues() throws Exception {
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			EnvironmentTestUtils.addEnvironment(context, "rabbit.enableRetry:false", "rabbit.queues:");
			context.register(Config.class);
			context.refresh();
			fail("BeanCreationException expected");
		}
		catch (Exception e) {
			assertThat(e, instanceOf(BeanCreationException.class));
			assertThat(e.getMessage(), containsString("At least one queue is required"));
		}
	}

	@Configuration
	@EnableConfigurationProperties(RabbitSourceProperties.class)
	static class Config {

	}

}

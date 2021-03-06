/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.amqp.rabbit.listener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.springframework.amqp.rabbit.config.RabbitListenerContainerTestFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;

/**
 *
 * @author Stephane Nicoll
 * @author Gary Russell
 */
public class RabbitListenerEndpointRegistryTests {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	private final RabbitListenerEndpointRegistry registry = new RabbitListenerEndpointRegistry();

	private final RabbitListenerContainerTestFactory containerFactory = new RabbitListenerContainerTestFactory();

	@Test
	public void createWithNullEndpoint() {
		thrown.expect(IllegalArgumentException.class);
		registry.registerListenerContainer(null, containerFactory);
	}

	@Test
	public void createWithNullEndpointId() {
		thrown.expect(IllegalArgumentException.class);
		registry.registerListenerContainer(new SimpleRabbitListenerEndpoint(), containerFactory);
	}

	@Test
	public void createWithNullContainerFactory() {
		thrown.expect(IllegalArgumentException.class);
		registry.registerListenerContainer(createEndpoint("foo", "myDestination"), null);
	}

	@Test
	public void createWithDuplicateEndpointId() {
		registry.registerListenerContainer(createEndpoint("test", "queue"), containerFactory);

		thrown.expect(IllegalStateException.class);
		registry.registerListenerContainer(createEndpoint("test", "queue"), containerFactory);
	}

	private SimpleRabbitListenerEndpoint createEndpoint(String id, String queueName) {
		SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
		endpoint.setId(id);
		endpoint.setQueueNames(queueName);
		return endpoint;
	}

}

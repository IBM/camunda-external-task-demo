/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.getstarted.chargecard;

import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;


import jizg.me.camunda_embedded_engine_demo_model.PaymentRequest;

public class SimpleChargeCardWorker {
	private final static Logger LOGGER = Logger.getLogger(SimpleChargeCardWorker.class.getName());

	public static void main(String[] args) {
//		final int CLIENT_NUMBER = System.getenv("CLIENT_NUMBER") != null ? Integer.parseInt(System.getenv("CLIENT_NUMBER")) : 3;
		final String BASE_URL = System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "http://localhost:8080/engine-rest";
		final long LOCK_DURATION = System.getenv("LOCK_DURATION") != null ? Long.parseLong(System.getenv("LOCK_DURATION")) : 20L; //default lock duration as 20 seconds
		final long LONG_POLLING_TIMEOUT = System.getenv("LONG_POLLING_TIMEOUT") != null ? Long.parseLong(System.getenv("LONG_POLLING_TIMEOUT")) : 60L; //default long polling timeout as 60 seconds
				
		ExternalTaskClient client = ExternalTaskClient.create()
				.baseUrl(BASE_URL)
				.asyncResponseTimeout(1000L * LONG_POLLING_TIMEOUT) // long polling timeout
				.maxTasks(1)
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("charge-card")
				.lockDuration(1000 * LOCK_DURATION) // the default lock duration is 20 seconds, but you can override this
				.handler((externalTask, externalTaskService) -> {
					// Put your business logic here

					LOGGER.info(externalTask.getProcessInstanceId() + " Task performed on " + new Date());
					
					// Complete the task
					externalTaskService.complete(externalTask);
				})
				.open();
		}
}
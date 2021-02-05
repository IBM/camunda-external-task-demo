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
package org.camunda.bpm.getstarted.checkitem;

import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;


import jizg.me.camunda_embedded_engine_demo_model.PaymentRequest;

public class SimpleCheckItemWorker {
	private final static Logger LOGGER = Logger.getLogger(SimpleCheckItemWorker.class.getName());

	public static void main(String[] args) {
//		final int CLIENT_NUMBER = System.getenv("CLIENT_NUMBER") != null ? Integer.parseInt(System.getenv("CLIENT_NUMBER")) : 3;
		final String BASE_URL = System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") :  "http://localhost:8080/engine-rest";//"http://localhost:9080/camunda-embedded-engine-demo/webapi";
		final long LOCK_DURATION = System.getenv("LOCK_DURATION") != null ? Long.parseLong(System.getenv("LOCK_DURATION")) : 20L; //default lock duration as 20 seconds
		final long LONG_POLLING_TIMEOUT = System.getenv("LONG_POLLING_TIMEOUT") != null ? Long.parseLong(System.getenv("LONG_POLLING_TIMEOUT")) : 60L; //default long polling timeout as 60 seconds
				
		ExternalTaskClient client = ExternalTaskClient.create()
				.baseUrl(BASE_URL)
				.asyncResponseTimeout(1000L * LONG_POLLING_TIMEOUT) // long polling timeout
				.maxTasks(1)
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("check-item")
				.lockDuration(1000 * LOCK_DURATION) // the default lock duration is 20 seconds, but you can override this
				.handler((externalTask, externalTaskService) -> {
					// Put your business logic here

					LOGGER.info(externalTask.getProcessInstanceId() + " Task performed on " + new Date());
					
					// Complete the task
					externalTaskService.complete(externalTask);
				})
				.open();
		}
	
	/**
     * Thread that actually generates the given load
     * @author Sriram
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
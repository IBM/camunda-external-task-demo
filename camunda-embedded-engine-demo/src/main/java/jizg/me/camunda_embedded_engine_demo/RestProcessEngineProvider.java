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
package jizg.me.camunda_embedded_engine_demo;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.rest.spi.ProcessEngineProvider;

import jizg.me.camunda_embedded_engine_demo.camunda.BPM_Engine;

public class RestProcessEngineProvider implements ProcessEngineProvider {

	public ProcessEngine getDefaultProcessEngine() {
		return BPM_Engine.getProcessEngine();
	}

	public ProcessEngine getProcessEngine(String name) {
		return BPM_Engine.getProcessEngine();
	}

	public Set<String> getProcessEngineNames() {
		Set<String> names = new HashSet<String>();
		names.add(BPM_Engine.getProcessEngine().getName());
		return names;
	}

}

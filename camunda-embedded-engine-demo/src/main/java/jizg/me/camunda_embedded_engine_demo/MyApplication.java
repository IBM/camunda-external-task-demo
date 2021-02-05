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

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.camunda.bpm.engine.rest.impl.CamundaRestResources;

import jizg.me.camunda_embedded_engine_demo.camunda.BPM_Engine;

@ApplicationPath("/webapi/*")
public class MyApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
	  BPM_Engine.DeployProcesses(); 
	  
	  Set<Class<?>> classes = new HashSet<Class<?>>();
	  classes.add(MyResource.class);
	  classes.addAll(CamundaRestResources.getResourceClasses());
	  classes.addAll(CamundaRestResources.getConfigurationClasses());
	
	  return classes;
  }

}
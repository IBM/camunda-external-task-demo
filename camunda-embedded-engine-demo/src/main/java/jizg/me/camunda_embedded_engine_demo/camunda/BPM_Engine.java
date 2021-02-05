package jizg.me.camunda_embedded_engine_demo.camunda;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;

public class BPM_Engine {
	
	private static ProcessEngine processEngine;
	
	
	
	public static ProcessEngine getProcessEngine() {
		
		if (processEngine == null) {
			try {
				String jndi = "jdbc/bpmds";
				
				StandaloneProcessEngineConfiguration config = new StandaloneProcessEngineConfiguration();
				config
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
				  .setJobExecutorActivate(true);
				config.setDataSourceJndiName(jndi);
				
				
				List<ProcessEnginePlugin> processEnginePlugins = new ArrayList<>();
				SpinProcessEnginePlugin spinProcessEnginePlugin = new SpinProcessEnginePlugin();
				processEnginePlugins.add(spinProcessEnginePlugin);
				config.setProcessEnginePlugins(processEnginePlugins);
				
				processEngine = config.buildProcessEngine();
				  
				  
	    	} catch (Exception ex) {
	    		System.out.println("BPM_Engine getProcessEngine ERROR: "+ ex.getMessage());
	    		throw ex;
	    	}
		}
		
		
		return processEngine;
		
	}
	
	public static void DeployProcesses() {
		RepositoryService repositoryService = getProcessEngine().getRepositoryService();
		
		try {
			ProcessDefinition process = repositoryService.createProcessDefinitionQuery()
					.processDefinitionKey("payment-retrieval")
					.latestVersion()
					.singleResult();
			
			if (process == null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream is = classLoader.getResourceAsStream("payment.bpmn");
				BpmnModelInstance payment = Bpmn.readModelFromStream(is);
				repositoryService.createDeployment()
				.addModelInstance("payment.bpmn", payment)
				.deploy();
				System.out.println("payment retrieval process definition is deployed");
			} else {
				System.out.println("payment retrieval process definition is activated");
			}
		} catch (RuntimeException ex) {
			System.out.println("BPM_Engine DeployProcesses ERROR: "+ ex.getMessage());
			throw ex;
		}
	}
}

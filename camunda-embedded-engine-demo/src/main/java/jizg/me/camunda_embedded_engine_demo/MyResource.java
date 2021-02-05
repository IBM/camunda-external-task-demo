package jizg.me.camunda_embedded_engine_demo;

import java.net.URI;
import java.text.MessageFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import jizg.me.camunda_embedded_engine_demo_model.PaymentRequest;
import jizg.me.camunda_embedded_engine_demo.camunda.BPM_Engine;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    public Response startProcess(PaymentRequest payment, @Context UriInfo info) {
    	
    	ProcessEngine ProcEngine = BPM_Engine.getProcessEngine();
    	
    	ObjectValue paymentJson =
    			  Variables.objectValue(payment).serializationDataFormat("application/json").create();
    	
    	ProcessInstance processInstance = ProcEngine.getRuntimeService().createProcessInstanceByKey("payment-retrieval")
  			.setVariable("paymentRequest", paymentJson)
  			.executeWithVariablesInReturn();
    	
    	String id = processInstance.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        String message = MessageFormat.format("process is started(process id= {0})", id);
        System.out.println(message);
        return Response.created(uri).entity(message).build();
    }
}

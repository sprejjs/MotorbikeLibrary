package Services;

//import JMS.JmsService;
import Model.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by vspreys on 18/06/16.
 */
@Path("/motorbikes")
@Component
@SpringBootApplication
@EnableJms
public class MotorbikeService {

    @Bean
    JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getClichedMessage() {
        return new Gson().toJson(ApplicationModel.GetMakes());
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String addModel(@HeaderParam("make") String make, @HeaderParam("model") String model,
                           @HeaderParam("modelClass") String modelClass, @HeaderParam("engineSize") int engineSize) {
        if (make == null) {
            return getJsonResponse("make is not supplied", false);
        }

        if (model == null) {
            return getJsonResponse("model is not supplied", false);
        }

        if (modelClass == null) {
            return getJsonResponse("modelClass is not supplied", false);
        }

        if (engineSize == 0) {
            return getJsonResponse("engineSize is not supplied", false);
        }

        Model newModel = new Model(model, modelClass, engineSize);
        if(ApplicationModel.AddModel(make, newModel)) {
            return getJsonResponse("Model has been added successfully", true);
        } else {
            return getJsonResponse("Could not add the model", false);
        }
    }

    private String getJsonResponse(String message, boolean success) {
        return new Gson().toJson(new Response(message, success));
    }

    @JmsListener(destination = "motorbike-makes", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(String message) {
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        if (message.equals("CREATE_MODEL")) {
            ApplicationModel.CreateModel();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        //Send JMS message
        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(MotorbikeService.class, args);

        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("CREATE_MODEL");
            }
        };
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println("Sending a new message.");
        jmsTemplate.send("motorbike-makes", messageCreator);

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/makes");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}

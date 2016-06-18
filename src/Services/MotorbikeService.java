package Services;

import Model.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by vspreys on 18/06/16.
 */
@Path("/motorbikes")
public class MotorbikeService {

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
            return getJsonResponse("Model has been added successufully", true);
        } else {
            return getJsonResponse("Could not add the model", false);
        }
    }

    private String getJsonResponse(String message, boolean success) {
        return new Gson().toJson(new Response(message, success));
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/makes");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}

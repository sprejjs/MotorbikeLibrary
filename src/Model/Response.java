package Model;

/**
 * Created by vspreys on 18/06/16.
 */
public class Response {
    private boolean success;
    private String message;

    public Response(String message, boolean success) {
        this.success = success;
        this.message = message;
    }
}

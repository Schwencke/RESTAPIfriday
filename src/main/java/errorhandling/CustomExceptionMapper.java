package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ExceptionDTO err;
    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof PersonNotFoundException) {
            Logger.getLogger(PersonNotFoundException.class.getName())
                    .log(Level.SEVERE, null, exception);
            err = new ExceptionDTO(404, exception.getMessage());
        }
        if (exception instanceof MissingFieldsException) {
            Logger.getLogger(MissingFieldsException.class.getName())
                    .log(Level.SEVERE, null, exception);
            err = new ExceptionDTO(404, exception.getMessage());
        }

        return Response
                .status(err.getCode())
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}

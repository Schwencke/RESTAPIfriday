package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonsDTO;
import dtos.PersonDTO;
import errorhandling.ExceptionDTO;
import errorhandling.NewException;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("person")
public class PersonResource {

    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String a) {
        PersonDTO rd = GSON.fromJson(a, PersonDTO.class);
        PersonDTO result = FACADE.addPerson(rd.getfName(),rd.getlName(),rd.getPhone());
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOnId(@PathParam("id")Integer id) {
        PersonDTO rd = FACADE.getPerson(id);
        return Response.ok().entity(GSON.toJson(rd)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id")Integer id, String a) {
        PersonDTO psd = GSON.fromJson(a, PersonDTO.class);
        psd.setId(id);
        PersonDTO result = FACADE.editPerson(psd);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@PathParam("id") Integer id) throws ExceptionDTO {
        PersonDTO result = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }
}

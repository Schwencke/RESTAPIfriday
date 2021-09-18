package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
    public Response create(String a) throws MissingFieldsException {
        PersonDTO psd = GSON.fromJson(a, PersonDTO.class);
        if (GSON.fromJson(a,PersonDTO.class).getfName() == null || GSON.fromJson(a, PersonDTO.class).getlName() == null)
            throw new MissingFieldsException("Firstname and/or lastname is missing");
        if (psd.getSrt() != null && psd.getZp() != null && psd.getCt() != null)
        {
            PersonDTO result = FACADE.addPersonWithAdress(psd.getfName(),psd.getlName(),psd.getPhone(), psd.getSrt(), psd.getZp(),psd.getCt());
            return Response.ok().entity(GSON.toJson(result)).build();
        } else {
            PersonDTO result = FACADE.addPerson(psd.getfName(), psd.getlName(), psd.getPhone());
            return Response.ok().entity(GSON.toJson(result)).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOnId(@PathParam("id")Integer id) throws PersonNotFoundException {
        PersonDTO psd = FACADE.getPerson(id);
        return Response.ok().entity(GSON.toJson(psd)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id")Integer id, String a) throws MissingFieldsException {
        PersonDTO psd = GSON.fromJson(a, PersonDTO.class);
        if (psd.getfName() == null || psd.getlName() == null) throw new MissingFieldsException("Firstname and/or lastname is missing");
        psd.setId(id);
        PersonDTO result = FACADE.editPerson(psd);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete (@PathParam("id") Integer id) throws PersonNotFoundException {
        PersonDTO result = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }
}

package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo alter path to appname
@Path("xxx")
public class RenameMeResource {

    private  EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private  FacadeExample FACADE =  FacadeExample.getFacadeExample(EMF);
    private  Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


//
//    @GET
//    @Path("all")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAll() {
//        List<DTOCLASS> rns = FACADE.getAll();
//        return Response.ok().entity(GSON.toJson(rns)).build();
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getOnId(@PathParam("id")Long id) {
//        DTOCLASS rd = FACADE.getById(id);
//        return Response.ok().entity(GSON.toJson(rd)).build();
//    }
//
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response create(String a) {
//        DTOCLASS rd = GSON.fromJson(a, DTOCLASS.class);
//        DTOCLASS result = FACADE.create(rd);
//        return Response.ok().entity(GSON.toJson(result)).build();
//    }
//
//    @PUT
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response update(@PathParam("id")Long id, String a) {
//        DTOCLASS rd = GSON.fromJson(a, DTOCLASS.class);
//        rd.setId(id);
//        DTOCLASS result = FACADE.update(rd);
//        return Response.ok().entity(GSON.toJson(result)).build();
//    }
//
//    @DELETE
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response delete (@PathParam("id") Long id) throws NewException {
//        DTOCLASS result = FACADE.delete(id);
//        return Response.ok().entity(GSON.toJson(result)).build();
//    }
}

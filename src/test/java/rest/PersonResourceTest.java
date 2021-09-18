package rest;


import dtos.PersonDTO;
import entities.Person;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.LinkedHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.Matchers.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person et,to,tre,fire,fem;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();
         et = new Person("Thomas", "Overgaard", "11");
         to = new Person("Per", "Pølsemand", "2");
         tre = new Person("Anders", "And", "33");
         fire = new Person("Lucky", "Lukas", "44");
         fem = new Person("Gurli", "Gris", "55");

        try {
            em.getTransaction().begin();
            em.persist(et);
            em.persist(to);
            em.persist(tre);
            em.persist(fire);
            em.persist(fem);
            em.getTransaction().commit();

        }
        finally {
            em.close();
        }
    }

    @Test
    public void testAll() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(5));
    }

    @Test
    public void findById(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/"+et.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Thomas"));
    }

    @Test
    public void deleteById(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .delete("/person/"+tre.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Anders"));
    }

    @Test
    public void updateById(){
        int id = et.getId();
        PersonDTO testp =  new PersonDTO(new Person("Test", "Testerson", "123456789"));

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testp)
                .when()
                .put("/person/"+id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Test"))
                .body("lName", equalTo("Testerson"))
                .body("phone", equalTo("123456789"));

    }

}

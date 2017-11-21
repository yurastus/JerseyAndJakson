import com.rest.model.Employee;
import com.rest.model.Project;
import com.rest.service.EmployeeService;
import com.rest.utils.EmployeeUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.File;
import static com.rest.utils.EmployeeUtils.EMPLOYEE_URL;
import static com.rest.utils.JacksonUtils.log;
import static com.rest.utils.JacksonUtils.toJson;

public class EmployeeApiTest extends JerseyTest {

    private String name = "McGregor";

    @BeforeMethod
    @Override
    public void setUp() throws Exception {
        super.setUp(); //for testNG relation
    }

    @AfterMethod
    @Override
    public void tearDown() throws Exception {
        super.tearDown(); //for testNG relation
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(EmployeeService.class);
    }

    /* Jersey tests */

    @Test
    public void getEmployeeTest(){
        Employee findCreatedEmployee = get(EMPLOYEE_URL + name, Employee.class);

        Assert.assertEquals(name, findCreatedEmployee.getName());
    }

    @Test
    public void createEmployeeTest(){
        Employee payload = EmployeeUtils.generateEmployee(name);
        Employee createdEmployee = create(EMPLOYEE_URL, payload);

        Assert.assertEquals(toJson(createdEmployee), toJson(payload));
    }

    @Test
    public void updateEmployeeTest() {
        Employee payload = EmployeeUtils.generateEmployee(name);
        payload.setOnBench(false);

        Employee updatedEmployee = update(EMPLOYEE_URL, payload);

        Assert.assertEquals(toJson(updatedEmployee), toJson(payload));
    }

    /* Jackson tests */

    @Test
    public void mapJacksonTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);

        String orderJson = "{\"id\":\"002\", \"description\": \"jackson description\"}";

        Project project = mapper.readValue(orderJson, Project.class);
        log(project);

        String unmapped = mapper.writeValueAsString(project);
        log(unmapped);
    }

    @Test
    public void deserializeJsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);

        Project[] hanksProjects = {Project.builder().id(01).description("one order").build(), Project.builder().id(02).description("two order").build()};
        Employee user = Employee.builder().name("Hank").age(36).isOnBench(true).pastProjects(hanksProjects).build();

        mapper.writeValue(new File("MyJson.json"), user);

        Employee readFromFileOrder = mapper.readValue(new File("Employee.json"), Employee.class);

        String prettyUser = mapper.writeValueAsString(readFromFileOrder);
        log(prettyUser);
        JsonNode node = mapper.readTree(prettyUser);
        node.findValue("01");
    }


    @Test
    public void jsonGeneratorTest() throws Exception{
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator = factory.createJsonGenerator(new File("Employee.json"), JsonEncoding.UTF8);
        generator.getOutputTarget();

        generator.writeStartObject();
        generator.writeStringField("name", "McGregor");
        generator.writeEndObject();
    }

    //-------------------------------------------------------------

    private <T>T get(String url, Class<T> clazz){
        log("GET");
        return target(url).request().accept(MediaType.APPLICATION_JSON).get(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T>T create(String url, T payload){
        log("POST");
        return target(url).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(payload), (Class<T>) payload.getClass());
    }

    @SuppressWarnings("unchecked")
    private <T>T update(String url, T payload){
        log("UPDATE");
        return target(url).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(payload), (Class<T>) payload.getClass());
    }

    private <T>T delete(String url, Class<T> clazz){
        log("DELETE");
        return target(url).request().accept(MediaType.APPLICATION_JSON).delete(clazz);
    }


}
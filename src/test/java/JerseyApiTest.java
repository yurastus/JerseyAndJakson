import com.rest.model.Employee;
import com.rest.service.EmployeeService;
import com.rest.utils.EmployeeUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import static com.rest.utils.EmployeeUtils.EMPLOYEE_URL;
import static com.rest.utils.EmployeeUtils.TEST_NAME;
import static com.rest.utils.JacksonUtils.toJson;

public class JerseyApiTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(EmployeeService.class);
    }

    @Test
    public void testGetEmployee(){
        Employee findCreatedEmployee = target(EMPLOYEE_URL + TEST_NAME).request().get(Employee.class);

        Assert.assertEquals(TEST_NAME, findCreatedEmployee.getName());
    }

    @Test
    public void testCreateEmployee(){
        Employee payload = EmployeeUtils.generateEmployee(TEST_NAME);
        Employee createdEmployee = target(EMPLOYEE_URL).request().post(Entity.json(payload), Employee.class);

        Assert.assertEquals(toJson(createdEmployee), toJson(payload));
    }

    @Test
    public void testUpdateEmployee() {
        Employee payload = EmployeeUtils.generateEmployee(TEST_NAME);
        payload.setOnBench(false);

        Employee updatedEmployee = target(EMPLOYEE_URL).request().put(Entity.json(payload), Employee.class);

        Assert.assertEquals(toJson(updatedEmployee), toJson(payload));
    }


}
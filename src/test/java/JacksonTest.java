import com.rest.model.Employee;
import com.rest.utils.EmployeeUtils;
import com.rest.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.rest.utils.EmployeeUtils.*;
import static com.rest.utils.JacksonUtils.toJson;

public class JacksonTest {

    @Test
    public void objectAndJsonMappingTest() {

        //added line
        //added line 2
        //added line 3
        Employee employee = EmployeeUtils.generateEmployee(TEST_NAME);
        String jsonEmployee = toJson(employee);
        Employee objectEmployee = JacksonUtils.fromJson(jsonEmployee, Employee.class);

        Assert.assertEquals(objectEmployee, employee);
    }

    @Test
    public void writeReadJsonFromFileTest() {

        Employee expectedEmployee = EmployeeUtils.generateEmployee(TEST_NAME);
        JacksonUtils.writeJsonToFile(EMPLOYEE_JSON, expectedEmployee);
        Employee readFromFileEmployee = JacksonUtils.readJsonFromFile(EMPLOYEE_JSON, Employee.class);

        Assert.assertEquals(JacksonUtils.toJson(readFromFileEmployee), JacksonUtils.toJson(expectedEmployee));
    }

}
package com.rest.service;

import com.rest.model.Employee;
import com.rest.utils.EmployeeUtils;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(EmployeeUtils.EMPLOYEE_URL)
public class EmployeeService {

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee get(@PathParam("name") String name) {
        return EmployeeUtils.generateEmployee(name);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee create(Employee employee) {
        return employee;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee update(Employee employee) {
        return employee;
    }

    @DELETE
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("name") String name) {
        return Response.ok("Deleted with name " + name).build();
    }

}
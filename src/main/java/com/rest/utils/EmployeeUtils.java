package com.rest.utils;

import com.rest.model.Employee;
import com.rest.model.Project;

public class EmployeeUtils {

    public static final String EMPLOYEE_URL = "employee/";


    public static Employee generateEmployee(String name){
        return Employee.builder()
                .name(name).age(31).isOnBench(true).pastProjects(generateTwoProjects())
                .build();
    }

    private static Project[] generateTwoProjects(){
        return new Project[]{
                Project.builder().id(10).description("Embedded").build(),
                Project.builder().id(20).description("Web").build()
        };
    }

}
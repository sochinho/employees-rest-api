package com.braintri.employees.web;

import com.braintri.employees.EmployeesRestApiApplicationTests;
import com.braintri.employees.converter.PositionDTOConverter;
import com.braintri.employees.model.Employee;
import com.braintri.employees.model.Position;
import com.braintri.employees.repository.EmployeeRepository;
import com.braintri.employees.repository.PositionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeesRestApiApplicationTests.class)
public class PositionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionDTOConverter positionDTOConverter;

    private String firstName1 = "firstName1", firstName2 = "firstName2";
    private String lastName1 = "lastName1", lastName2 = "lastName2";
    private String mail1 = "email1@mail.com", mail2 = "email2@mail.com";

    private Employee employee1, employee2;
    private Position position1, position2;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        position1 = Position.builder()
                .description("firstDescription")
                .build();

        position2 = Position.builder()
                .description("secondDescription")
                .build();

        employee1 = Employee.builder()
                .firstName(firstName1)
                .lastName(lastName1)
                .email(mail1)
                .position(position1)
                .build();

        employee2 = Employee.builder()
                .firstName(firstName2)
                .lastName(lastName2)
                .email(mail2)
                .position(position2)
                .build();

        List<Employee> employees1 = new ArrayList<>();
        employees1.add(employee1);

        List<Employee> employees2 = new ArrayList<>();
        employees1.add(employee2);

        position1.setEmployees(employees1);
        position2.setEmployees(employees2);

        employeeRepository.saveAll(Arrays.asList(employee1, employee2));

        positionRepository.saveAll(Arrays.asList(position1, position2));
    }

    @Test
    public void test_getAllPositions_Then_ReturnOk() throws Exception {

        mockMvc.perform(
                get("/positions/all")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", iterableWithSize(2)))
                .andExpect(jsonPath("$[0].description", is(position1.getDescription())))
                .andExpect(jsonPath("$[0].numberOfEmployees", is(position1.getEmployees().size())))
                .andExpect(jsonPath("$[1].description", is(position2.getDescription())))
                .andExpect(jsonPath("$[1].numberOfEmployees", is(position2.getEmployees().size())));
    }
}

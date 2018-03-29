package com.braintri.employees.web;

import com.braintri.employees.EmployeesRestApiApplicationTests;
import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.model.Employee;
import com.braintri.employees.model.Position;
import com.braintri.employees.repository.EmployeeRepository;
import com.braintri.employees.repository.PositionRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeesRestApiApplicationTests.class)
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    private Gson gson = new GsonBuilder().create();

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

        employeeRepository.saveAll(Arrays.asList(employee1, employee2));

        positionRepository.saveAll(Arrays.asList(position1, position2));
    }

    @Test
    public void test_getAllEmployees_Then_ReturnOk() throws Exception {

        mockMvc.perform(
                get("/employees/all")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", iterableWithSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(employee1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(employee1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(employee1.getEmail())))
                .andExpect(jsonPath("$[0].positionId", is(employee1.getPosition().getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(employee2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(employee2.getLastName())))
                .andExpect(jsonPath("$[1].email", is(employee2.getEmail())))
                .andExpect(jsonPath("$[1].positionId", is(employee2.getPosition().getId().intValue())));
    }

    @Test
    public void test_findEmployees_Then_ReturnOk() throws Exception {

        mockMvc.perform(
                get("/employees/search?lastName=" + employee1.getLastName())
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", iterableWithSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(employee1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(employee1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(employee1.getEmail())))
                .andExpect(jsonPath("$[0].positionId", is(employee1.getPosition().getId().intValue())));

        mockMvc.perform(
                get("/employees/search?email=" + employee2.getEmail())
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", iterableWithSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(employee2.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(employee2.getLastName())))
                .andExpect(jsonPath("$[0].email", is(employee2.getEmail())))
                .andExpect(jsonPath("$[0].positionId", is(employee2.getPosition().getId().intValue())));

        mockMvc.perform(
                get("/employees/search?email=abc")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", iterableWithSize(0)));
    }

    @Test
    public void test_postEmployee_Then_ReturnOk() throws Exception {

        String firstName = "newFirstName";
        String lastName = "newLastName";
        String mail = "newMail";
        Long positionId = position1.getId();

        EmployeeCreationDTO employeeCreationDTO =
                new EmployeeCreationDTO(firstName, lastName, mail, positionId);

        mockMvc.perform(
                post("/employees/post")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(gson.toJson(employeeCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName", is(employeeCreationDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employeeCreationDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(employeeCreationDTO.getEmail())))
                .andExpect(jsonPath("$.positionId", is(employeeCreationDTO.getPositionId().intValue())));
    }

    @Test
    public void test_postEmployee_When_PositionDoesNotExist_Then_ReturnNotFound() throws Exception {

        String firstName = "newFirstName";
        String lastName = "newLastName";
        String mail = "newMail";
        Long positionId = 200L;

        EmployeeCreationDTO employeeCreationDTO =
                new EmployeeCreationDTO(firstName, lastName, mail, positionId);

        mockMvc.perform(
                post("/employees/post")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(gson.toJson(employeeCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }

    @Test
    public void test_deleteEmployee_Then_ReturnOk() throws Exception {

        mockMvc.perform(
                delete("/employees/delete/{id}", employee1.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteEmployee_When_EmployeeDoesNotExist_Then_ReturnNotFound() throws Exception {

        mockMvc.perform(
                delete("/employees/delete/{id}", 200L)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

}

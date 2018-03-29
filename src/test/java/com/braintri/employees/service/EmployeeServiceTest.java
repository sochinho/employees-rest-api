package com.braintri.employees.service;

import com.braintri.employees.converter.CreationDTOEmployeeConverter;
import com.braintri.employees.converter.EmployeeDTOConverter;
import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.dto.EmployeeDTO;
import com.braintri.employees.exception.EmployeeNotFoundException;
import com.braintri.employees.model.Employee;
import com.braintri.employees.model.Position;
import com.braintri.employees.model.QEmployee;
import com.braintri.employees.repository.EmployeeRepository;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeDTOConverter employeeDTOConverter;

    @Mock
    private CreationDTOEmployeeConverter creationDTOEmployeeConverter;

    private String firstName1 = "firstName1", firstName2 = "firstName2";
    private String lastName1 = "lastName1", lastName2 = "lastName2";
    private String mail1 = "email1@mail.com", mail2 = "email2@mail.com";

    private Employee employee1, employee2;
    private Position position1, position2;

    @Before
    public void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeDTOConverter, creationDTOEmployeeConverter);

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

        EmployeeDTO employeeDto1 = EmployeeDTO.builder()
                .firstName(firstName1)
                .lastName(lastName1)
                .email(mail1)
                .positionId(position1.getId())
                .build();

        EmployeeDTO employeeDto2 = EmployeeDTO.builder()
                .firstName(firstName2)
                .lastName(lastName2)
                .email(mail2)
                .positionId(position2.getId())
                .build();

        employee1.setId(1L);
        employee2.setId(2L);

        position1.setEmployees(Collections.singletonList(employee1));
        position2.setEmployees(Collections.singletonList(employee2));

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
        when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.of(employee2));

        when(employeeDTOConverter.convert(employee1)).thenReturn(employeeDto1);
        when(employeeDTOConverter.convert(employee2)).thenReturn(employeeDto2);
    }

    @Test
    public void testGetAllEmployees() {

        List<EmployeeDTO> employeesDTO = employeeService.getAllEmployees();

        assertEquals(2, employeesDTO.size());
        assertEquals(firstName1, employeesDTO.get(0).getFirstName());
        assertEquals(lastName1, employeesDTO.get(0).getLastName());
        assertEquals(mail1, employeesDTO.get(0).getEmail());
        assertEquals(position1.getId(), employeesDTO.get(0).getPositionId());
        assertEquals(firstName2, employeesDTO.get(1).getFirstName());
        assertEquals(lastName2, employeesDTO.get(1).getLastName());
        assertEquals(mail2, employeesDTO.get(1).getEmail());
        assertEquals(position2.getId(), employeesDTO.get(1).getPositionId());
    }

    @Test
    public void testFindEmployees() {

        QEmployee qEmployee = QEmployee.employee;

        Predicate testFirstName = qEmployee.firstName.eq(firstName1);

        when(employeeRepository.findAll(testFirstName)).thenReturn(Collections.singletonList(employee1));

        List<EmployeeDTO> employeesDTO = employeeService.findEmployees(testFirstName);

        assertEquals(1, employeesDTO.size());
        assertEquals(firstName1, employeesDTO.get(0).getFirstName());
        assertEquals(lastName1, employeesDTO.get(0).getLastName());
        assertEquals(mail1, employeesDTO.get(0).getEmail());
        assertEquals(position1.getId(), employeesDTO.get(0).getPositionId());

        Predicate testLastName = qEmployee.lastName.eq(lastName2);

        when(employeeRepository.findAll(testLastName)).thenReturn(Collections.singletonList(employee2));

        employeesDTO = employeeService.findEmployees(testLastName);

        assertEquals(1, employeesDTO.size());
        assertEquals(firstName2, employeesDTO.get(0).getFirstName());
        assertEquals(lastName2, employeesDTO.get(0).getLastName());
        assertEquals(mail2, employeesDTO.get(0).getEmail());
        assertEquals(position2.getId(), employeesDTO.get(0).getPositionId());
    }

    @Test
    public void testCreateEmployee() {

        String firstName = "newFirstName";
        String lastName = "newLastName";
        String mail = "newMail";
        Long positionId = position1.getId();

        EmployeeCreationDTO employeeCreationDTO =
                new EmployeeCreationDTO(firstName, lastName, mail, positionId);

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(mail)
                .position(position1)
                .build();

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(mail)
                .positionId(positionId)
                .build();

        when(creationDTOEmployeeConverter.convert(employeeCreationDTO)).thenReturn(employee);

        when(employeeDTOConverter.convert(employee)).thenReturn(employeeDTO);

        EmployeeDTO employeeDTOResult = employeeService.createEmployee(employeeCreationDTO);

        assertEquals(firstName, employeeDTOResult.getFirstName());
        assertEquals(lastName, employeeDTOResult.getLastName());
        assertEquals(mail, employeeDTOResult.getEmail());
        assertEquals(positionId, employeeDTOResult.getPositionId());
    }

    @Test
    public void testDeleteEmployee() {

        employeeService.deleteEmployee(employee1.getId());
        employeeService.deleteEmployee(employee2.getId());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testFailDeleteEmployee() {

        Long wrongId = 200L;

        employeeService.deleteEmployee(wrongId);
    }
}

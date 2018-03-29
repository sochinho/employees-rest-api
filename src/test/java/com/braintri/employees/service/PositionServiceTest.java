package com.braintri.employees.service;

import com.braintri.employees.converter.PositionDTOConverter;
import com.braintri.employees.dto.PositionDTO;
import com.braintri.employees.model.Employee;
import com.braintri.employees.model.Position;
import com.braintri.employees.repository.PositionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PositionServiceTest {

    private PositionService positionService;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private PositionDTOConverter positionDTOConverter;

    private String description1 = "firstDescription", description2 = "secondDescription";
    private String firstName1 = "firstName1", firstName2 = "firstName2";
    private String lastName1 = "lastName1", lastName2 = "lastName2";
    private String mail1 = "email1@mail.com", mail2 = "email1@mail.com";

    private Employee employee1, employee2;
    private Position position1, position2;
    private PositionDTO positionDto1, positionDto2;

    @Before
    public void setUp() {

        positionService = new PositionServiceImpl(positionRepository, positionDTOConverter);

        position1 = Position.builder()
                .description(description1)
                .build();

        position2 = Position.builder()
                .description(description2)
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

        position1.setEmployees(Collections.singletonList(employee1));
        position2.setEmployees(Collections.singletonList(employee2));

        positionDto1 = PositionDTO.builder()
                .description(description1)
                .numberOfEmployees(1L)
                .build();

        positionDto2 = PositionDTO.builder()
                .description(description2)
                .numberOfEmployees(1L)
                .build();

        when(positionDTOConverter.convert(position1)).thenReturn(positionDto1);
        when(positionDTOConverter.convert(position2)).thenReturn(positionDto2);

        when(positionRepository.findAll()).thenReturn(Arrays.asList(position1, position2));
    }

    @Test
    public void testGetAllPositions() {
        List<PositionDTO> positionsDto = positionService.getAllPositions();

        assertEquals(2, positionsDto.size());
        assertEquals(description1, positionsDto.get(0).getDescription());
        assertEquals(1L, positionsDto.get(0).getNumberOfEmployees().longValue());
        assertEquals(description2, positionsDto.get(1).getDescription());
        assertEquals(1L, positionsDto.get(0).getNumberOfEmployees().longValue());
    }
}

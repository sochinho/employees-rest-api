package com.braintri.employees.converter;

import com.braintri.employees.dto.PositionDTO;
import com.braintri.employees.model.Position;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PositionDTOConverter implements Converter<Position, PositionDTO> {

    @Override
    public PositionDTO convert(Position position) {
        return PositionDTO.builder()
                .description(position.getDescription())
                .numberOfEmployees((long) position.getEmployees().size())
                .build();
    }
}

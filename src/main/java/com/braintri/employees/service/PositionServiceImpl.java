package com.braintri.employees.service;

import com.braintri.employees.converter.PositionDTOConverter;
import com.braintri.employees.dto.PositionDTO;
import com.braintri.employees.repository.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PositionServiceImpl implements PositionService {

    private PositionRepository positionRepository;

    private PositionDTOConverter positionDTOConverter;

    @Override
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(positionDTOConverter::convert).collect(Collectors.toList());
    }
}

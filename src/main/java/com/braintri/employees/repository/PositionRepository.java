package com.braintri.employees.repository;

import com.braintri.employees.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {


}

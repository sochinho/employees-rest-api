package com.braintri.employees.web;

import com.braintri.employees.dto.EmployeeCreationDTO;
import com.braintri.employees.dto.EmployeeDTO;
import com.braintri.employees.model.Employee;
import com.braintri.employees.service.EmployeeService;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> findEmployees(@QuerydslPredicate(root = Employee.class) Predicate predicate) {
        return ResponseEntity.ok(employeeService.findEmployees(predicate));
    }

    @PostMapping("/post")
    public ResponseEntity<EmployeeDTO> postEmployee(@Valid @RequestBody EmployeeCreationDTO employeeCreationDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeCreationDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.ok().build();
    }
}

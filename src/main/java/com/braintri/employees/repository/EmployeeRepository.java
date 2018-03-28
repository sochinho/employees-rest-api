package com.braintri.employees.repository;

import com.braintri.employees.model.Employee;
import com.braintri.employees.model.QEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee>, QuerydslBinderCustomizer<QEmployee> {

    @Override
    default void customize(QuerydslBindings bindings, QEmployee root) {
        bindings.including(root.firstName, root.lastName, root.email);
    }
}

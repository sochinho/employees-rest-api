package com.braintri.employees.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;
}

package org.example.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter
@ToString
@AllArgsConstructor
public class Seller implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Double baseSalary;

    private Department department;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Seller that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

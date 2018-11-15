package com.roguskip.roguskiwarehouse.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode()
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table()
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name" )
    private String name;
}


package com.roguskip.roguskiwarehouse.warehouse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode()
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table()
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "name" )
    private String name;

    @Column(name = "extra_price")
    private BigDecimal extraPrice;
}

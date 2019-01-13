package com.roguskip.roguskiwarehouse.operation;

import com.roguskip.roguskiwarehouse.model.Audit;
import com.roguskip.roguskiwarehouse.model.OperationName;
import com.roguskip.roguskiwarehouse.model.ProductView;
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
public class Operation extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String operationName;

    @Transient
    private ProductView productView;

    @Transient
    private Integer quantity;
}

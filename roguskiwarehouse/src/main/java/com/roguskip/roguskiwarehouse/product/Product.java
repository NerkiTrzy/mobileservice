package com.roguskip.roguskiwarehouse.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roguskip.roguskiwarehouse.manufacturer.Manufacturer;
import com.roguskip.roguskiwarehouse.model.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@EqualsAndHashCode()
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table()
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries(
        {
                @NamedQuery(name = Product.Queries.GET_ALL_PRODUCTS,
                                query = Product.Queries.GET_ALL_PRODUCTS_QUERY)
        }
)
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "name" )
    private String name;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id" )
    @JsonIgnore
    private Manufacturer manufacturer;

    static class Queries {
        static final String GET_ALL_PRODUCTS = "Product.getAllProducts";

        static final String GET_ALL_PRODUCTS_QUERY =
                "SELECT new map( \n" +
                        "p.id AS productId, \n" +
                        "p.name AS productName, \n" +
                        "p.currency AS currency, \n" +
                        "p.price AS price, \n" +
                        "p.quantity as quantity, \n" +
                        "m.id AS manufacturerId, \n" +
                        "m.name AS manufacturerName, \n" +
                        "p.uuid AS productGUID \n" +
                ") \n" +
                "FROM Product p \n" +
                "JOIN Manufacturer m ON m.id = p.manufacturer.id\n" +
                "ORDER BY m.name, p.name";
    }
}

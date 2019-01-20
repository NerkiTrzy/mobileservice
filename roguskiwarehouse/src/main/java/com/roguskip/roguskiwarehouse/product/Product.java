package com.roguskip.roguskiwarehouse.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roguskip.roguskiwarehouse.manufacturer.Manufacturer;
import com.roguskip.roguskiwarehouse.model.Audit;
import com.roguskip.roguskiwarehouse.warehouse.Warehouse;
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

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id" )
    @JsonIgnore
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "warehouse_id" )
    @JsonIgnore
    private Warehouse warehouse;

    static class Queries {
        static final String GET_ALL_PRODUCTS = "Product.getAllProducts";

        static final String GET_ALL_PRODUCTS_QUERY =
                "SELECT new map( \n" +
                        "p.id AS productId, \n" +
                        "p.name || ' ' || COALESCE(w.name, '') AS productName, \n" +
                        "p.currency AS currency, \n" +
                        "p.price + COALESCE(w.extraPrice, 0) AS price, \n" +
                        "p.quantity as quantity, \n" +
                        "m.id AS manufacturerId, \n" +
                        "m.name AS manufacturerName, \n" +
                        "p.uuid AS productGUID, \n" +
                        "COALESCE(p.color, 'Not specified') AS color \n," +
                        "coalesce(w.id, 0) AS warehouseId, \n" +
                        "coalesce(w.name, 'Not specified') AS warehouseName \n" +
                ") \n" +
                "FROM Product p \n" +
                "JOIN Manufacturer m ON m.id = p.manufacturer.id \n" +
                "LEFT JOIN Warehouse w ON w.id = p.warehouse.id \n" +
                "ORDER BY m.name, p.name";
    }
}

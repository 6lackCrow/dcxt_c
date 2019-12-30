package xyz.crowxx.dcxtcomplete.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
public class OrderFood
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long order_id;
    @Column(nullable = false)
    private Long food_id;
    @Column(nullable = false)
    private Integer number;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal price;

}

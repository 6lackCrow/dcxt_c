package xyz.crowxx.dcxtcomplete.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long user_id;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal price;
    @Column(precision = 10, scale = 2,nullable = false)
    private BigDecimal promotion;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private Integer is_pay;
    @Column(nullable = false)
    private Integer is_taken;
    private String comment;
    @Column(nullable = false)
    private String create_time;
    @Column(nullable = false)
    private String pay_time;
    @Column(nullable = false)
    private String taken_time;
}

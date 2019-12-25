package xyz.crowxx.dcxtcomplete.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    private String create_time;
}

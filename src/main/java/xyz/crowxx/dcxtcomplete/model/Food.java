package xyz.crowxx.dcxtcomplete.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long category_id;
    private String name;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    private String image_url;
    private Integer status;
    private String create_time;
}

package xyz.crowxx.dcxtcomplete.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodVO {
    private Long id;
    private Long category_id;
    private String name;
    private BigDecimal price;
    private String image_url;
    private Integer status;
    private String create_time;
    private Integer count;
}

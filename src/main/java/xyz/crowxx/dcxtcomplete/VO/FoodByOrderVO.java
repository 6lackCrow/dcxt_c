package xyz.crowxx.dcxtcomplete.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodByOrderVO {
    private Long id;
    private String name; // 商品名
    private BigDecimal price; // 单价
    private Integer number; // 数量
    private BigDecimal sum;//单件商品总和
}

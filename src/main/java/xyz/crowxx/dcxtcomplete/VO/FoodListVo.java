package xyz.crowxx.dcxtcomplete.VO;

import lombok.Data;
import xyz.crowxx.dcxtcomplete.model.Food;

import java.util.List;

@Data
public class FoodListVo {
    private Long category_id;
    private String category_name;
    private List<Food> food;
}

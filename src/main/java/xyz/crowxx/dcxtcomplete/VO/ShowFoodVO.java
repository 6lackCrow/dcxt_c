package xyz.crowxx.dcxtcomplete.VO;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class ShowFoodVO {
    /*                    <th width="200">标题</th>
    <th width="200">图片</th>
                    <th width="146">分类</th>
                    <th width="130">创建时间</th>
                    <th width="50">价格</th>
                    <th width="50">状态</th>*/
    private Long id;
    private String category;
    private String imgUrl;
    private Long category_id;
    private String name;
    private BigDecimal price;
    private String status;
    private String create_time;
}

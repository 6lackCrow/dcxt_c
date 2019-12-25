package xyz.crowxx.dcxtcomplete.VO;

import lombok.Data;

@Data
public class GetUserInfoVO {
    private String session_key;
    private String openid;
}

package practice.vo.useraddr;

import lombok.Data;

@Data
public class UserAddrVo {
    private String province;
    private String city;
    private String area;
    private String addr;
    private String receiverMobile;
    private String receiverName;
    private String commonAddr;
}

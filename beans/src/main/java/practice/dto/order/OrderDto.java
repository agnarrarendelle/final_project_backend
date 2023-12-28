package practice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    String orderRemark;
    String receiverAddress;
    String receiverMobile;
    String receiverName;
    List<Integer> cartIds;
}

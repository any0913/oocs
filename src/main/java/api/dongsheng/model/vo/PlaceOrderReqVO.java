package api.dongsheng.model.vo;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: aioh_sw_oocs
 * @description: 下订单vo
 * @author: urbane
 * @create: 2020-04-18 11:26
 **/
@Data
public class PlaceOrderReqVO {


    /**
     * 用户身份唯一标识
     */
    @NotNull(message = "用户身份唯一标识不能为空")
    @NotBlank(message = "用户身份唯一标识不能为空")
    private String passport;

    /**
     * 付款用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @NotBlank(message = "用户ID不能为空")
    private String userid;

    /**
     * 设备ID，暂存数据用于牛云方案会员权益激活
     */
    @NotNull(message = "设备ID不能为空")
    @NotBlank(message = "设备ID不能为空")
    private String device_id;

    /**
     * 用户token，暂存数据用于牛云方案会员权益激活
     */
    @NotNull(message = "token不能为空")
    @NotBlank(message = "token不能为空")
    private String token;

    /**
     * 服务提供商 "KG" "KW" "QM";分别为 酷狗,酷我,Q音，暂存数据用于牛云方案会员权益激活
     */
    @NotNull(message = "服务提供商不能为空")
    @NotBlank(message = "服务提供商不能为空")
    private String sp;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    @NotBlank(message = "商品ID不能为空")
    private String goodsId;

}

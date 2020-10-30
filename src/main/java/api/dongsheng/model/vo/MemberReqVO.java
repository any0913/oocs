package api.dongsheng.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: aioh_sw_oocs
 * @description: 获取会员列表
 * @author: urbane
 * @create: 2020-04-22 10:23
 **/
@Data
public class MemberReqVO {
    /**
     * 服务提供商 "KG" "KW" "QM";分别为 酷狗,酷我,Q音，暂存数据用于牛云方案会员权益激活
     */
    @NotNull(message = "服务提供商不能为空")
    @NotBlank(message = "服务提供商不能为空")
    private String sp;
}

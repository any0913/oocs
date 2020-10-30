package api.dongsheng.controller;

import api.dongsheng.exception.BaseException;
import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.service.ResourceService;
import api.dongsheng.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author rx
 * @Description 订单
 * @Date 2019/8/8 17:28
 **/
@RestController
@RequestMapping("/v2/open/ocs")
public class OrderController {

    @Autowired
    private ResourceService lrtsService;

    /**
     * 同步订单
     */
    @RequestMapping("/syncOrder")
    public RetResult syncOrder(HttpServletRequest request) {
        RetResult result = new RetResult();
        Map<String, Object> map = StringUtil.getParam(request);
        if (StringUtils.isBlank(map.get("os").toString()) || StringUtils.isBlank(map.get(StringUtil.PASSPORT).toString())
                || StringUtils.isBlank(map.get("orderNo").toString()) || StringUtils.isBlank(map.get("orderType").toString())
                || StringUtils.isBlank(map.get("goodsType").toString()) || StringUtils.isBlank(map.get("albumId").toString())
                || StringUtils.isBlank(map.get("quantity").toString()) || StringUtils.isBlank(map.get("originalFee").toString())
                || StringUtils.isBlank(map.get("totalFee").toString()) || StringUtils.isBlank(map.get("status").toString())) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        if(map.get("orderType").toString().equals("2") && StringUtils.isEmpty(map.get("musicId").toString())){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        lrtsService.syncOrder(map);
        return result;
    }
}

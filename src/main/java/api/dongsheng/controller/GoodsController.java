package api.dongsheng.controller;

import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.enums.TMEISPEnum;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.entity.Member;
import api.dongsheng.model.vo.MemberReqVO;
import api.dongsheng.service.GoodsService;
import api.dongsheng.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: aioh_sw_oocs
 * @description: 商品相关API入口
 * @author: urbane
 * @create: 2020-04-17 16:41
 **/
@Slf4j
@RestController(value = "GoodsController")
@RequestMapping(value = "/v2/open/ocs/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping(value = "/getMemberList")
    public RetResult getMemberList(@Valid @RequestBody MemberReqVO memberReqVO, HttpServletRequest request){
        String channelId = request.getHeader(StringUtil.CHANNELID);
        String iSP = TMEISPEnum.getSp(memberReqVO.getSp());
        if (iSP == null) {
            log.error("请求参数错误：sp{}",memberReqVO.getSp());
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        List<Member> memberList = goodsService.getMemberList(memberReqVO.getSp(), channelId);
        return new RetResult(memberList);
    }
}

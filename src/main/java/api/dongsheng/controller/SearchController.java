package api.dongsheng.controller;

import api.dongsheng.exception.BaseException;
import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.service.SearchService;
import api.dongsheng.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author rx
 * @Description 搜索接口
 * @Date 2019/8/8 17:28
 **/
@RestController
@RequestMapping("/v2/open/ocs")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索
     */
    @RequestMapping("/search")
    public RetResult search(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT))
                || StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORTTYPE)) || StringUtils.isEmpty(request.getParameter("name"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        typeInvalid(request);
        Map<String, Object> param = StringUtil.getParam(request);
        Map<String, Object> map = searchService.search(param);
        if(Integer.valueOf(map.get("code").toString()) == 0){
            map.remove("code");
            result.setData(map);
        }else{
            result.setCode(Integer.valueOf(map.get("code").toString()));
            if(map.containsKey("msg")){
                result.setMsg(map.get("msg").toString());
            }else{
                result.setMsg("fail");
            }
        }
        return result;
    }

    /**
     * @Author rx
     * @Description  搜索
     * @Date 11:30 2019/12/13
     **/
    private void typeInvalid(HttpServletRequest request) {
        if(request.getParameter(StringUtil.PASSPORTTYPE).equals("2") && StringUtils.isEmpty(request.getParameter(StringUtil.DEVICE_IP))){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
    }
}

package api.dongsheng.controller;

import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.entity.ChannelCategory;
import api.dongsheng.model.vo.ProgramVO;
import api.dongsheng.model.vo.RadioVO;
import api.dongsheng.service.RadioService;
import api.dongsheng.service.ResourceService;
import api.dongsheng.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author rx
 * @Description api接口
 * @Date 2019/8/6 19:38
 **/
@RestController
@RequestMapping("/v2/open/ocs/radio")
public class RadioController {


    @Autowired
    private RadioService radioService;
    @Autowired
    private ResourceService resourceService;


    /**
     * 获取电台分类
     * @param request
     * @return
     */
    @RequestMapping("/getCategory")
    public RetResult getCategory(HttpServletRequest request) {
        RetResult result = new RetResult();
        Map<String, Object> map = StringUtil.getParam(request);
        map.put("type",1);
        List<ChannelCategory> value = resourceService.getCategory(map);
        result.setData(value);
        return result;
    }



    /**
     * 获取分类下专辑
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRadio")
    public RetResult getRadio(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.SOURCEID))
                || StringUtils.isEmpty(request.getParameter(StringUtil.CATEGORYID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        List<RadioVO> albums = radioService.getRadio(map);
        result.setData(albums);
        return result;
    }


    /**
     * 获取电台下音频
     *
     * @param request
     * @return
     */
    @RequestMapping("/getProgram")
    public RetResult getProgram(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.SOURCEID))
                || StringUtils.isEmpty(request.getParameter(StringUtil.RADIOID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        List<ProgramVO> musics = radioService.getProgram(map);
        result.setData(musics);
        return result;
    }



    /**
     * 获取分类下专辑
     *
     * @param request
     * @return
     */
    @RequestMapping("/get")
    public RetResult getRadioList(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getHeader(StringUtil.CATEGORYID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Integer categoryId = Integer.valueOf(request.getHeader(StringUtil.CATEGORYID));
        List<Integer> albums = radioService.getRadioList(categoryId);
        result.setData(albums);
        return result;
    }


    /**
     * 获取电台播放地址
     * @param request
     * @return
     */
    @RequestMapping("/url")
    public RetResult getRadioUrl(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getHeader(StringUtil.RADIOID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        String radioId = request.getHeader(StringUtil.RADIOID);
        return radioService.getRadioUrl(radioId);
    }
}

package api.dongsheng.service;


import api.dongsheng.common.RetResult;
import api.dongsheng.model.vo.MusicVO;
import api.dongsheng.model.vo.ProgramVO;
import api.dongsheng.model.vo.RadioVO;

import java.util.List;
import java.util.Map;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
public interface RadioService {

    /**
     * 获取电台
     * @param map
     * @return
     */
    List<RadioVO> getRadio(Map<String, Object> map);

    /**
     * 获取电台下节目
     * @param map
     * @return
     */
    List<ProgramVO> getProgram(Map<String, Object> map);

    /**
     * 获取电台播放连接
     * @param radioId
     * @return
     */
    RetResult getRadioUrl(String radioId);

    /**
     * 获取电台
     * @param categoryId
     * @return
     */
    List<Integer> getRadioList(Integer categoryId);
}

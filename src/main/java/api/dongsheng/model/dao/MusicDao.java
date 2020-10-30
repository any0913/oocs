package api.dongsheng.model.dao;


import api.dongsheng.model.entity.Music;
import api.dongsheng.model.mapper.MusicMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:18
 **/
@Repository
public class MusicDao {

    @Resource
    private MusicMapper musicMapper;


    /**
     * 获取专辑下音频
     *
     * @param map
     * @return
     */
    public List<Music> findMusicList(Map<String, Object> map) {
        return musicMapper.findMusicList(map);
    }

    /**
     * 根据音频id和专辑id查询音频信息
     *
     * @param params
     * @return
     */
    public Music findMusicById(Map<String, Object> params) {
        return musicMapper.findMusicById(params);
    }
}

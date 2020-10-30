package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.Music;

import java.util.List;
import java.util.Map;

public interface MusicMapper {


    /**
     * 根据专辑id获取音频
     *
     * @param map
     * @return
     */
    List<Music> findMusicList(Map<String, Object> map);

    /**
     * 新增音频信息
     *
     * @param music
     */
    void insertMusic(Music music);

    /**
     * 查询
     *
     * @param musicId
     * @return
     */
    Music findMusic(Long musicId);

    /**
     * 根据音频id和专辑id查询音频信息
     *
     * @param params
     * @return
     */
    Music findMusicById(Map<String, Object> params);

    /**
     * 根据专辑和音频序号查询(适用于懒人)
     *
     * @param music
     * @return
     */
    Music findMusicBySection(Music music);

    /**
     * 获取所有音频资源
     * @return
     */
    List<Music> selectMusicList();

    /**
     * 批量修改音频
     * @param music
     */
    void updateList(Music music);
}

package api.dongsheng.model.dao;


import api.dongsheng.model.mapper.AlbumMapper;
import api.dongsheng.model.entity.Album;
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
public class AlbumDao {

    @Resource
    private AlbumMapper albumMapper;


    /**
     * 查询
     *
     * @param map
     * @return
     */
    public Album findAlbum(Map<String,Object> map) {
        return albumMapper.findAlbum(map);
    }


    /**
     * 获取分类下的专辑
     *
     * @param map
     * @return
     */
    public List<Album> findAlbumList(Map<String, Object> map) {
        return albumMapper.findAlbumList(map);
    }

}

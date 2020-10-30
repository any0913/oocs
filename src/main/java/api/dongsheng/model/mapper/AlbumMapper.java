package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumMapper {


    /**
     * 根据来源获取所有专辑
     *
     * @return
     */
    List<Album> findAll(Integer sourceId);


    /**
     * 根据分类id获取专辑
     *
     * @param map
     * @return
     */
    List<Album> findAlbumList(Map<String, Object> map);

    /**
     * 新增专辑信息
     *
     * @param album
     */
    void insertAlbum(Album album);

    /**
     * 查询
     *
     * @param map
     * @return
     */
    Album findAlbum(Map<String,Object> map);

    /**
     * 修改专辑
     *
     * @param a
     */
    void updateAlbum(Album a);

}

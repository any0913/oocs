package api.dongsheng.service;

import api.dongsheng.common.RetResult;
import api.dongsheng.model.entity.ChannelCategory;
import api.dongsheng.model.vo.AlbumVO;
import api.dongsheng.model.vo.MusicVO;
import api.dongsheng.model.vo.RadioVO;

import java.util.List;
import java.util.Map;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/7 15:33
 **/
public interface ResourceService {


    /**
     * 获取所有分类
     *
     * @param map
     * @return
     */
    List<ChannelCategory> getCategory(Map<String, Object> map);

    /**
     * 获取分类下书籍
     *
     * @param map
     * @return
     */
    List<AlbumVO> albumTypeList(Map<String, Object> map);

    /**
     * 获取书籍下章节
     *
     * @param map
     * @return
     */
    List<MusicVO> musicList(Map<String, Object> map);

    /**
     * 同步订单
     *
     * @param map
     * @return
     */
    void syncOrder(Map<String, Object> map);

    /**
     * 获取章节播放地址
     *
     * @param map
     * @return
     */
    Map<String, Object> entityPath(Map<String, Object> map);

    /**
     * 使用兑换码
     *
     * @param map
     */
    void useCode(Map<String, Object> map);


    /**
     * 生成指定渠道数量的兑换码
     *
     * @param generateNum
     * @param channelId
     */
    void generateCode(Long generateNum, Long channelId);

    /**
     * 获取歌词
     * @param map
     * @return
     */
    String getLyric(Map<String, Object> map);

    /**
     * 激活牛方案会员
     * @param map
     */
    String activate(Map<String, Object> map);

    /**
     * 刷新token
     * @param map
     * @return
     */
    String refreshToken(Map<String, Object> map);


    /**
     * 获取分类列表
     * @param map
     * @return
     */
    RetResult getCategoryList(Map<String, Object> map);

    /**
     * 获取分类歌单
     * @param map
     * @return
     */
    RetResult getCategorySpecial(Map<String, Object> map);

    /**
     * 获取歌单歌曲
     * @param map
     * @return
     */
    RetResult getSpecialSong(Map<String, Object> map);


    /**
     * 获取歌手列表
     * @param map
     * @return
     */
    RetResult getSingerList(Map<String, Object> map);

    /**
     * 获取歌手单曲
     * @param map
     * @return
     */
    RetResult getSingerSong(Map<String, Object> map);

    /**
     * 获取歌手专辑
     * @param map
     * @return
     */
    RetResult getSingerAlbum(Map<String, Object> map);

    /**
     * 获取排行榜
     * @param map
     * @return
     */
    RetResult getRankList(Map<String, Object> map);

    /**
     * 获取榜单歌曲
     * @param map
     * @return
     */
    RetResult getRankSong(Map<String, Object> map);


    /**
     * 获取推荐电台
     * @param map
     * @return
     */
    RetResult getFmList(Map<String, Object> map);

    /**
     * 获取电台歌曲
     * @param map
     * @return
     */
    RetResult getFmSong(Map<String, Object> map);

    /**
     * 收藏歌曲
     * @param map
     * @return
     */
    RetResult collectSong(Map<String, Object> map);

    /**
     * 获取收藏列表
     * @param map
     * @return
     */
    RetResult getCollectList(Map<String, Object> map);

    /**
     * 修改license绑定的用户
     * @param map
     * @return
     */
    RetResult updatePassport(Map<String, Object> map);

    /**
     * 激活酷码
     * @param map
     * @return
     */
    RetResult activateCode(Map<String, Object> map);

    /**
     * 批量查询歌曲权限
     * @param map
     * @return
     */
    RetResult getResPrivilege(Map<String, Object> map);

    /**
     * 获取酷狗用户信息
     * @param map
     * @return
     */
    RetResult getUserInfo(Map<String, Object> map);
}

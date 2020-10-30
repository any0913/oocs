package api.dongsheng.service.impl;


import ai.dongsheng.id.tool.core.IdTemplate;
import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.common.TmeCattleConstant;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dao.TencentActivateDao;
import api.dongsheng.model.entity.*;
import api.dongsheng.model.mapper.ChannelProductMapper;
import api.dongsheng.model.mapper.ChannelQuotaMapper;
import api.dongsheng.model.mapper.MembershipRightsErrorMapper;
import api.dongsheng.model.mapper.PaymentOrderMapper;
import api.dongsheng.service.TmeCattleService;
import api.dongsheng.util.HttpUtil;
import api.dongsheng.util.StringUtil;
import api.dongsheng.util.TmeCattleUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
@Slf4j
@Service
public class TmeCattleServiceImpl implements TmeCattleService {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;
    @Autowired
    private MembershipRightsErrorMapper membershipRightsErrorMapper;
    @Autowired
    private TencentActivateDao tencentActivateDao;
    @Autowired
    private ChannelQuotaMapper channelQuotaMapper;
    @Autowired
    private ChannelProductMapper channelProductMapper;

    @Value("${service.license.url}")
    private String serviceLicenseUrl;

    /**
     * 获取用户登录二维码
     * @param json
     * @return
     */
    @Override
    public RetResult getUserLogOnQrcode(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getUserLogOnQrcode(json));
    }


    /**
     * 获取渠道产品对应的产品信息
     * @param json
     * @return
     */
    private synchronized JSONObject getChannelProduct(JSONObject json){
        ChannelProduct channelProduct = new ChannelProduct();
        channelProduct.setChannel_id(json.getLong(StringUtil.CHANNELID));
        channelProduct.setProduct_id(json.getLong(StringUtil.PRODUCTID));
        ChannelProduct product = channelProductMapper.selectChannelProduct(channelProduct);
        if(product == null){
            throw new BaseException(RetCode.PRODUCT_NOT_FOUND.getCode(),RetCode.PRODUCT_NOT_FOUND.getMsg());
        }else{
            json.put(TmeCattleConstant.TME_PID,product.getTme_pid());
            json.put(TmeCattleConstant.TME_APPKEY,product.getTme_appkey());
            json.put(TmeCattleConstant.TME_MEMBER_KEY,product.getTme_member_key());
        }
        return json;
    }


    /**
     * 登录二维码授权信息
     * @param json
     * @return
     */
    @Override
    public RetResult qrCodeAuth(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getUserQrcodeAuth(json));
    }

    /**
     * 用户信息
     * @param json
     * @return
     */
    @Override
    public RetResult getUserInfo(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getUserInfo(json));
    }

    /**
     * 用户登出
     * @param json
     * @return
     */
    @Override
    public RetResult logout(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getUserLogout(json));
    }

    /**
     * 激活设备
     * @param json
     * @return
     */
    @Override
    public RetResult deviceActivation(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        insertActivateRecord(json);
        return getRetResult(TmeCattleUtil.deviceActivation(json));
    }


    /**
     * 新增激活记录
     * @param json
     */
    private synchronized void insertActivateRecord(JSONObject json){
        TencentActivate activate = tencentActivateDao.getTencentActivateBySn(json.getString(TmeCattleConstant.DEVICEID));
        if(activate == null){
            Long channelId = Long.valueOf(json.getInteger(StringUtil.CHANNELID));
            activate = new TencentActivate();
            activate.setPassport(json.getString(StringUtil.PASSPORT));
            activate.setChannelId(channelId);
            activate.setSn(json.getString(TmeCattleConstant.DEVICEID));
            activate.setKguserId(json.getString(TmeCattleConstant.USERID));
            activate.setToken(json.getString(TmeCattleConstant.TOKEN));
            tencentActivateDao.insertTencentActivate(activate);
        }
    }

    /**
     * 激活赠送会员
     * @param json
     * @return
     */
    public RetResult activationVip(JSONObject json){
        RetResult retResult = new RetResult();
        // 判断当前设备是否领取赠送会员
        Map<String,Object> map = new HashMap<>();
        map.put("channelId",json.getInteger(StringUtil.CHANNELID));
        map.put("deviceId",json.getString(TmeCattleConstant.DEVICEID));
        PaymentOrder paymentOrder = paymentOrderMapper.selectPaymentOrderByDeviceId(map);
        if(paymentOrder == null){
            // 根据渠道查询该渠道的配额
            JSONObject tmeRespJson = getChannelQuota(json);
            Integer error_code = tmeRespJson.getInteger("error_code");
            if (!error_code.equals(0)) {
                // 返回
                retResult.setCode(tmeRespJson.getInteger("error_code"));
                retResult.setMsg(tmeRespJson.getString("error_msg"));
            } else {
                // 返回
                JSONObject data = tmeRespJson.getJSONObject("data");
                JSONObject resultData = new JSONObject();
                resultData.put("create_time",data.getString("create_time"));
                resultData.put("vip_end_time",data.getString("vip_end_time"));
                retResult.setData(resultData);
            }
        }else{
            throw new BaseException(RetCode.DELICE_RECEIVE_MEMBER.getCode(),RetCode.DELICE_RECEIVE_MEMBER.getMsg());
        }
        return retResult;
    }

    /**
     *  配额判断并使用
     * @param json
     */
    private synchronized JSONObject getChannelQuota(JSONObject json){
        // 获取渠道产品信息
        getChannelProduct(json);
        Long channelId = Long.valueOf(json.getInteger(StringUtil.CHANNELID));
        ChannelQuota channelQuota = channelQuotaMapper.selectChannelQuota(channelId);
        if(channelQuota != null){
            // 配额存在判断是否还有额度
            if(channelQuota.getChannel_quota() > 0){
                switch (channelQuota.getQuota_unit()){
                    case 1 : json.put("day",channelQuota.getQuota_mode());break;
                    case 2 : json.put("month",channelQuota.getQuota_mode());break;
                    default:break;
                }
                // 充值会员
                Long id = IdTemplate.nextId();
                String datetime = new SimpleDateFormat("yyyyMMddHH").format(new Date());
                String payOrderId = new StringBuffer().append(datetime).append(id).toString();
                json.put("partner_no",payOrderId);
                json.put("orderAmount",channelQuota.getQuota_money());
                JSONObject tmeRespJson = giveVip(json,40);
                if(tmeRespJson.getInteger("error_code") == 0){
                    // 充值成功，配额额度减去-1
                    channelQuotaMapper.updateChannelQuota(channelQuota);
                }
                return tmeRespJson;
            }else{
                //配额已用完
                throw new BaseException(RetCode.QUOTA_USE_UP.getCode(), RetCode.QUOTA_USE_UP.getMsg());
            }
        }else{
            // 配额已失效
            throw new BaseException(RetCode.QUOTA_LAPSE.getCode(), RetCode.QUOTA_LAPSE.getMsg());
        }
    }


    /**
     * 会员直充并记录
     * @param json
     * @param payType 类型10 : 支付宝扫码支付，20：微信扫码支付,30：配额支付，40激活设备领取赠送的1个月会员
     * @return
     */
    private synchronized JSONObject giveVip(JSONObject json,int payType){
        String member = TmeCattleUtil.giveVip(json);
        JSONObject tmeRespJson = JSONObject.parseObject(member);
        Integer error_code = tmeRespJson.getInteger("error_code");
        if (!error_code.equals(0)) {
            // 会员直充失败，记录失败原因
            MembershipRightsError membershipRightsError = new MembershipRightsError();
            membershipRightsError.setPaymentOrderId(json.getString("partner_no"));
            membershipRightsError.setRequestingInformation(json.toJSONString());
            membershipRightsError.setErrorMessage(tmeRespJson.toJSONString());
            membershipRightsErrorMapper.insertSelective(membershipRightsError);
        } else {
            // 会员直充成功，生成订单记录，用于对账
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setChannelId(json.getLong(StringUtil.CHANNELID));
            paymentOrder.setProductId(json.getLong(StringUtil.PRODUCTID));
            paymentOrder.setPassport(json.getString(TmeCattleConstant.PASSPORT));
            paymentOrder.setUserId(json.getString(TmeCattleConstant.USERID));
            paymentOrder.setUserIp(json.getString(TmeCattleConstant.CLIENTIP));
            paymentOrder.setDeviceId(json.getString(TmeCattleConstant.DEVICEID));
            paymentOrder.setToken(json.getString(TmeCattleConstant.TOKEN));
            paymentOrder.setSp(json.getString(TmeCattleConstant.SP));
            paymentOrder.setGoodsId(json.getString("partner_no"));
            paymentOrder.setOrderAmount(json.getInteger("orderAmount"));
            paymentOrder.setIssuedRights((byte) 1);
            paymentOrder.setPayType((byte)payType);
            paymentOrder.setPaymentStatus((byte)2);
            paymentOrder.setPaymentTime(new Timestamp(System.currentTimeMillis()));
            paymentOrder.setPaymentOrderId(json.getString("partner_no"));
            paymentOrderMapper.insertSelective(paymentOrder);
        }
        return tmeRespJson;
    }



    /**
     * 使用配额
     * @param json
     * @return
     */
    @Override
    public RetResult useQuota(JSONObject json) {
        RetResult retResult = new RetResult();
        // 获取渠道产品信息
        getChannelProduct(json);
        String result = HttpUtil.doHttpPost(serviceLicenseUrl + StringUtil.CHANNEL_MEMBER_CODE,json);
        JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("data");
        if(jsonObject.getInteger("memberStatus") == 0){
            // 领取权益，会员直充并记录
            json.put("partner_no",json.getString("memberCode"));
            json.put("orderAmount",jsonObject.getString("memberMoney"));
            json.put("month",jsonObject.getInteger("memberMonth"));
            JSONObject tmeRespJson = giveVip(json,30);
            Integer error_code = tmeRespJson.getInteger("error_code");
            if (!error_code.equals(0)) {
                // 返回
                retResult.setCode(tmeRespJson.getInteger("error_code"));
                retResult.setMsg(tmeRespJson.getString("error_msg"));
            } else {
                // 返回
                JSONObject data = tmeRespJson.getJSONObject("data");
                JSONObject resultData = new JSONObject();
                resultData.put("create_time",data.getString("create_time"));
                resultData.put("vip_end_time",data.getString("vip_end_time"));
                retResult.setData(resultData);
            }
        }else if(jsonObject.getInteger("memberStatus") == 1){
            throw new BaseException(RetCode.QUOTA_USED.getCode(), RetCode.QUOTA_USED.getMsg());
        }else{
            throw new BaseException(RetCode.QUOTA_NOT_FOUND.getCode(), RetCode.QUOTA_NOT_FOUND.getMsg());
        }
        return retResult;
    }

    /**
     * 专辑信息
     * @param json
     * @return
     */
    @Override
    public RetResult albumInfo(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getAlbumInfo(json));
    }

    /**
     * 每日推荐
     * @param json
     * @return
     */
    @Override
    public RetResult everydayAwesome(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getEveryDayAwesome(json));
    }

    /**
     * 推荐歌单
     * @param json
     * @return
     */
    @Override
    public RetResult playlistAwesome(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getPlayList(json));
    }

    /**
     * 歌单内歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult playlistSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getPlayListSong(json));
    }

    /**
     * 电台列表
     * @param json
     * @return
     */
    @Override
    public RetResult radioList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getRadioList(json));
    }

    /**
     * 电台歌曲列表
     * @param json
     * @return
     */
    @Override
    public RetResult radioSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getRadioSong(json));
    }

    /**
     * 歌手列表
     * @param json
     * @return
     */
    @Override
    public RetResult singerList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSingerList(json));
    }

    /**
     * 歌手信息
     * @param json
     * @return
     */
    @Override
    public RetResult singerInfo(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSingerInfo(json));
    }

    /**
     * 歌手歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult singerSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSingerSong(json));
    }

    /**
     * 歌手专辑
     * @param json
     * @return
     */
    @Override
    public RetResult singerAlbum(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSingerAlbum(json));
    }

    /**
     * 批量查询歌曲信息
     * @param json
     * @return
     */
    @Override
    public RetResult songInfos(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSongInfos(json));
    }

    /**
     * 获取歌曲播放链接
     * @param json
     * @return
     */
    @Override
    public RetResult songUrl(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSongUrl(json));
    }

    /**
     * 获取歌曲歌词
     * @param json
     * @return
     */
    @Override
    public RetResult songLyric(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getSongLyric(json));
    }

    /**
     * 榜单列表
     * @param json
     * @return
     */
    @Override
    public RetResult topList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getTopList(json));
    }

    /**
     * 榜单歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult topSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getTopSong(json));
    }

    /**
     * 搜索歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult searchSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.searchSong(json));
    }

    /**
     * 自建歌单
     * @param json
     * @return
     */
    @Override
    public RetResult seftPlayList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getFavoritePlayList(json));
    }

    /**
     * 收藏歌单
     * @param json
     * @return
     */
    @Override
    public RetResult otherPlayList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getCollectPlayList(json));
    }

    /**
     * 收藏歌单内歌曲列表
     * @param json
     * @return
     */
    @Override
    public RetResult favoriteSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getCollectPlayListSong(json));
    }

    /**
     * 收藏或取消收藏歌单
     * @param json
     * @return
     */
    @Override
    public RetResult favoriteOperPlayList(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getFavoriteOperPlayList(json));
    }

    /**
     * 收藏或取消收藏歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult favoriteOperSong(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.getFavoriteOperSong(json));
    }

    /**
     * 收藏或取消收藏歌曲
     * @param json
     * @return
     */
    @Override
    public RetResult monitorListen(JSONObject json) {
        // 获取渠道产品信息
        getChannelProduct(json);
        return getRetResult(TmeCattleUtil.monitorListen(json));
    }

    /**
     * 返回
     * @param value 访问牛方案接口返回的数据
     * @return
     */
    private RetResult getRetResult(String value) {
        RetResult result = new RetResult();
        JSONObject json = JSONObject.parseObject(value);
        if(json.getInteger("error_code") == 0){
            result.setData(json.getJSONObject("data"));
        }else{
            result.setCode(json.getInteger("error_code"));
            result.setMsg(json.getString("error_msg"));
        }
        return result;
    }
}

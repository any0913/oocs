package api.dongsheng.model.mapper;


import api.dongsheng.model.entity.TencentActivate;

public interface TencentActivateMapper {

    /**
     * 根据设备获取userid和token
     * @param sn
     * @return
     */
    TencentActivate getTencentActivateBySn(String sn);

    /**
     * 新增激活信息
     * @param auth
     */
    void insertTencentActivate(TencentActivate auth);
}

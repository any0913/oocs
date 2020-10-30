package api.dongsheng.model.dao;

import api.dongsheng.model.entity.TencentActivate;
import api.dongsheng.model.mapper.TencentActivateMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/9 14:20
 **/
@Repository
public class TencentActivateDao {

    @Resource
    private TencentActivateMapper tencentActivateMapper;


    /**
     * 获取激活设备的userid和token
     * @param sn
     * @return
     */
    public TencentActivate getTencentActivateBySn(String sn) {
        return tencentActivateMapper.getTencentActivateBySn(sn);
    }

    /**
     * 新增激活信息
     * @param auth
     */
    public void insertTencentActivate(TencentActivate auth) {
        tencentActivateMapper.insertTencentActivate(auth);
    }
}

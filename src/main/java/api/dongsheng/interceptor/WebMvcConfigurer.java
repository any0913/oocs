package api.dongsheng.interceptor;

import api.dongsheng.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author rx
 * @Description 自定义拦截
 * @Date 2019/8/7 10:27
 **/
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private ChannelService channelService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(channelService)).addPathPatterns("/v2/open/**")
                .excludePathPatterns("/v2/open/ocs/getResPrivilege")
                .excludePathPatterns("/v2/open/ocs/radio/get")
                .excludePathPatterns("/v2/open/ocs/radio/url")
                .excludePathPatterns("/v2/open/ocs/payment/notify/alipay")
                .excludePathPatterns("/v2/open/ocs/payment/notify/wechat");
        super.addInterceptors(registry);
    }

}

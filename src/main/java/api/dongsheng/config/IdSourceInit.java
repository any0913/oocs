package api.dongsheng.config;

import ai.dongsheng.id.tool.properties.PropertiesFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IdSourceInit {
    @Value("${id.configure.url}")
    private String idUrl;

    @PostConstruct
    public void propertiesFactory() {
//        PropertiesFactory.getInstance().init(idUrl);
    }
}

package api.dongsheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("api.dongsheng.model.mapper")
public class OocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OocsApplication.class, args);
    }

}

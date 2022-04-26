package xxs.businesskey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//拦截sql，然后添加生成业务主键注入到实体对象中--基于数据库
//TODO 扩展：如果使用redis，那么就不需要应用内部的缓存了。可以直接实现BusinessKeyGenerator
@SpringBootApplication
public class BusinessKeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessKeyApplication.class, args);
    }
}

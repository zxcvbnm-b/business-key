package xxs.businesskey.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import xxs.businesskey.service.BusinessKeyService;

public class BusinessKeyTableInit  implements ApplicationRunner, ApplicationContextAware {
    private  ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BusinessKeyService businessKeyService = applicationContext.getBean(BusinessKeyService.class);
        Assert.notNull(businessKeyService,"开启了businessKeyService不能为空");
        /*创建表 ，添加索引*/
        if(!businessKeyService.existBusinessKeyTable()){
            businessKeyService.createBusinessKeyTable();
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext=applicationContext;
    }
}

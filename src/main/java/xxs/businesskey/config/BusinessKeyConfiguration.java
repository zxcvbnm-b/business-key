package xxs.businesskey.config;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import xxs.businesskey.BusinessKeyGeneratorManager;
import xxs.businesskey.generatorstrategy.DefaultBusinessKeyGeneratorStrategy;
import xxs.businesskey.interceptor.BusinessKeyInterceptor;
import xxs.businesskey.service.BusinessKeyService;


@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@ConditionalOnProperty(prefix = "business.key",name = "open",havingValue = "true")
public class BusinessKeyConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "business.key",name = "init",havingValue = "true")
    public BusinessKeyTableInit businessKeyTableInit() {
        return new BusinessKeyTableInit();
  }

    @Bean
    public BusinessKeyInterceptor businessKeyInterceptor() {
        return new BusinessKeyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(BusinessKeyGeneratorManager.class)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BusinessKeyGeneratorManager generateNextNumber(){
        return new BusinessKeyGeneratorManager();
    }
    //需要实现 BusinessKeyService的接口
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultBusinessKeyGeneratorStrategy defaultBusinessKeyGeneratorStrategy(@Autowired(required = true) BusinessKeyService businessKeyService) {
        return new DefaultBusinessKeyGeneratorStrategy(businessKeyService);
    }

}

package xxs.businesskey;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import xxs.businesskey.dto.BusinessKeyPropertiesDTO;
import xxs.businesskey.generatorstrategy.BusinessKeyGenerator;

import java.util.HashMap;
import java.util.Map;
public class BusinessKeyGeneratorManager implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Map<String, BusinessKeyGenerator> businessKeyGeneratorStrategyMap=new HashMap<>();
    public String getNextBusinessKey(BusinessKeyPropertiesDTO businessKeyPropertiesDTO) {
        BusinessKeyGenerator businessKeyGeneratorStrategy = businessKeyGeneratorStrategyMap.get(businessKeyPropertiesDTO.getStrategySimpleName());
        if(businessKeyGeneratorStrategy==null){
            businessKeyGeneratorStrategy = businessKeyGeneratorStrategyMap.get(null);
        }
        Assert.notNull(businessKeyGeneratorStrategy,"根据业务建名称获取不到生成策略");
        return businessKeyGeneratorStrategy.getNextBusinessKey(businessKeyPropertiesDTO);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化策略，初始表
        initBusinessKeyGeneratorStrategy();
    }

    private void initBusinessKeyGeneratorStrategy() {
        Map<String, BusinessKeyGenerator> businessKeyGeneratorMap = applicationContext.getBeansOfType(BusinessKeyGenerator.class);
        if(businessKeyGeneratorMap!=null){
            for (Map.Entry<String, BusinessKeyGenerator> item : businessKeyGeneratorMap.entrySet()) {
                businessKeyGeneratorStrategyMap.put(item.getKey(),item.getValue());
            }
        }

    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}

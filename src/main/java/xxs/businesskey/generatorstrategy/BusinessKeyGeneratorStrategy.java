package xxs.businesskey.generatorstrategy;


import org.springframework.util.Assert;
import xxs.businesskey.dto.BaseBusinessKeyEntity;
import xxs.businesskey.dto.BusinessKeyPropertiesDTO;
import xxs.businesskey.service.BusinessKeyService;

import java.util.UUID;

public abstract class BusinessKeyGeneratorStrategy implements BusinessKeyGenerator {

    private BusinessKeyService businessKeyService;

    public BusinessKeyGeneratorStrategy(BusinessKeyService businessKeyService) {
        this.businessKeyService = businessKeyService;
    }
   //生成下一个业务主键
    public   String getNextBusinessKey(BusinessKeyPropertiesDTO businessKeyPropertiesDTO){
        String businessKeyName=businessKeyPropertiesDTO.getBusinessKeyName();
        //1.行是否存在，如果不存在，那么创建
        this.createBusinessKeyRowIfNotExist(businessKeyName);
        //2.如果存在，判断是否是已经耗尽了缓存中的值，如果是，那么从数据库获取
        this.updateBusinessKeyRow(businessKeyPropertiesDTO);
        //3.生成当前值返回
        return this.getCurrBusinessKeyValue(businessKeyPropertiesDTO);
    }
    //更新业务行。
    private  void updateBusinessKeyRow(BusinessKeyPropertiesDTO businessKeyPropertiesDTO){
        boolean reload = isLoadNewBusinessKeyValue(businessKeyPropertiesDTO);
        String oldBusinessKeyBusinessKeyValue=null;
        if(reload){
            BaseBusinessKeyEntity oldBusinessKeyByBusinessKeyName = businessKeyService.getOldBusinessKeyByBusinessKeyName(businessKeyPropertiesDTO.getBusinessKeyName());
            Assert.notNull(oldBusinessKeyByBusinessKeyName,"根据业务键名称获取业务键失败");
            oldBusinessKeyBusinessKeyValue=oldBusinessKeyByBusinessKeyName.getBusinessKeyValue();
            //2.1生成新的业务键
            String newBusinessKeyValue =  this.getNewBusinessKeyBusinessKeyValue(businessKeyPropertiesDTO,oldBusinessKeyBusinessKeyValue);
            //2.2.更新数据库的值
            businessKeyService.updateBusinessKey(businessKeyPropertiesDTO.getBusinessKeyName(),newBusinessKeyValue);
        }
    }

    //如果不存在业务创建行，那么创建行
    private  void createBusinessKeyRowIfNotExist(String businessKeyName){
        boolean existBusinessKey = businessKeyService.existBusinessKeyBusinessKeyRow(businessKeyName);
        if(!existBusinessKey){
            BaseBusinessKeyEntity baseBusinessKeyEntity =new BaseBusinessKeyEntity();
            baseBusinessKeyEntity.setBusinessKeyName(businessKeyName);
            baseBusinessKeyEntity.setId(UUID.randomUUID().toString());
            businessKeyService.createBusinessKeyRow(baseBusinessKeyEntity);
        }
    }

    protected abstract String getCurrBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO);

    protected abstract String getNewBusinessKeyBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO,String oldBusinessKeyBusinessKeyValue);

    protected abstract boolean isLoadNewBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO);

}

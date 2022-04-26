package xxs.businesskey.service;

import xxs.businesskey.dto.BaseBusinessKeyEntity;

public interface BusinessKeyService {
    //创建表
    int createBusinessKeyTable();
    //判断是否存在表
    boolean existBusinessKeyTable();
    //判断是否存在业务行根据
    boolean existBusinessKeyBusinessKeyRow(String businessKeyName);

    void createBusinessKeyRow(BaseBusinessKeyEntity baseBusinessKeyEntity);

    BaseBusinessKeyEntity getOldBusinessKeyByBusinessKeyName(String businessKeyName);

    void updateBusinessKey(String businessKeyId, String newBusinessValue);
}

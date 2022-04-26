package xxs.businesskey.generatorstrategy;

import xxs.businesskey.dto.BusinessKeyPropertiesDTO;

public interface BusinessKeyGenerator {
     String getNextBusinessKey(BusinessKeyPropertiesDTO businessKeyPropertiesDTO);
}

package xxs.businesskey.generatorstrategy;



import xxs.businesskey.dto.BusinessKeyPropertiesDTO;
import xxs.businesskey.service.BusinessKeyService;
//默认生成策略，就是生成Long类型的，也可以改成XXX-时间-序列号的形式
public class DefaultBusinessKeyGeneratorStrategy  extends BusinessKeyGeneratorStrategy{
    protected int idBlockSize=5;
    protected long nextNumber;
    protected long lastNumber = -1;

    public DefaultBusinessKeyGeneratorStrategy(BusinessKeyService businessKeyService) {
        super(businessKeyService);
    }
    @Override
    protected String getCurrBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO) {
        return Long.toString(++nextNumber);
    }

    @Override
    protected String getNewBusinessKeyBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO,String oldBusinessKeyValue) {
        if(oldBusinessKeyValue==null){
            return (1+idBlockSize)+"";
        }
        Long newLastNumber = Long.valueOf(Long.valueOf(oldBusinessKeyValue)+idBlockSize);
        this.lastNumber=newLastNumber;
        return newLastNumber+"";
    }

    @Override
    protected  boolean isLoadNewBusinessKeyValue(BusinessKeyPropertiesDTO businessKeyPropertiesDTO) {
        if (lastNumber < nextNumber) {
         return true;
        }
        return false;
    }

    public static void main(String[] args) {
        DefaultBusinessKeyGeneratorStrategy strategy=new DefaultBusinessKeyGeneratorStrategy(null);
        Long tem=0l;
        for (Integer i = 0; i < 100 ; i++) {
            boolean reload = strategy.isLoadNewBusinessKeyValue(null);
            if(reload){
                String newBusinessKeyResourceValue = strategy.getNewBusinessKeyBusinessKeyValue(null,tem.toString());
                tem=Long.valueOf(newBusinessKeyResourceValue);
            }
            String currBusinessKey = strategy.getCurrBusinessKeyValue(null);
            System.out.println(currBusinessKey);
        }
    }
}

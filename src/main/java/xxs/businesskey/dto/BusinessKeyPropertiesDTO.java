package xxs.businesskey.dto;
//注解的配置信息
public class BusinessKeyPropertiesDTO {
    private  String businessKeyName;
    private  String businessKeyAttributeName;
    private  String strategySimpleName;
    private  String header;

    public String getBusinessKeyName() {
        return businessKeyName;
    }

    public void setBusinessKeyName(String businessKeyName) {
        this.businessKeyName = businessKeyName;
    }

    public String getBusinessKeyAttributeName() {
        return businessKeyAttributeName;
    }

    public void setBusinessKeyAttributeName(String businessKeyAttributeName) {
        this.businessKeyAttributeName = businessKeyAttributeName;
    }

    public String getStrategySimpleName() {
        return strategySimpleName;
    }

    public void setStrategySimpleName(String strategySimpleName) {
        this.strategySimpleName = strategySimpleName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

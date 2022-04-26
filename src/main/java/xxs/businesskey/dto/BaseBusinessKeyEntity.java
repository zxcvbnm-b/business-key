package xxs.businesskey.dto;
public class BaseBusinessKeyEntity {
    private String id;
    private String businessKeyName;
    private String businessKeyValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessKeyName() {
        return businessKeyName;
    }

    public void setBusinessKeyName(String businessKeyName) {
        this.businessKeyName = businessKeyName;
    }

    public String getBusinessKeyValue() {
        return businessKeyValue;
    }

    public void setBusinessKeyValue(String businessKeyValue) {
        this.businessKeyValue = businessKeyValue;
    }
}

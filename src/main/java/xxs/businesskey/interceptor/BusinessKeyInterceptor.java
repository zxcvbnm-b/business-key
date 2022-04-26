package xxs.businesskey.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import xxs.businesskey.BusinessKeyGeneratorManager;

import xxs.businesskey.annotation.BusinessKeyLogo;
import xxs.businesskey.annotation.BusinessKeyProperties;
import xxs.businesskey.dto.BusinessKeyPropertiesDTO;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class BusinessKeyInterceptor implements Interceptor {
    @Autowired
    BusinessKeyGeneratorManager businessKeyGeneratorManager;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            Object parameter = invocation.getArgs()[1];
            if (parameter instanceof DefaultSqlSession.StrictMap) {
                DefaultSqlSession.StrictMap paramMap = (DefaultSqlSession.StrictMap) parameter;
                List values = (List) paramMap.get("list");

                for (Iterator iterator = values.iterator(); iterator.hasNext(); ) {
                    Object value = iterator.next();
                    if (value instanceof BusinessKeyLogo) {
                        setInsertValue(value);
                    }
                }
            }

            if (parameter instanceof BusinessKeyLogo) {
                try{ setInsertValue(parameter);}catch (Exception e){e.printStackTrace();}
            }
        }
        return invocation.proceed();
    }

    /*判断是否有注解--然后进行获取并注入值*/
    private void setInsertValue(Object entity) throws Throwable {
        Class<?> entityClass = entity.getClass();
        BusinessKeyProperties businessKeyProperties = entityClass.getAnnotation(BusinessKeyProperties.class);
        if(businessKeyProperties!=null){
            String businessKeyAttributeName = businessKeyProperties.businessKeyAttributeName();
            BusinessKeyPropertiesDTO businessKeyPropertiesDTO=new BusinessKeyPropertiesDTO();
            businessKeyPropertiesDTO.setBusinessKeyAttributeName(businessKeyProperties.businessKeyAttributeName());
            businessKeyPropertiesDTO.setBusinessKeyName(businessKeyProperties.businessKeyName());
            businessKeyPropertiesDTO.setStrategySimpleName(businessKeyProperties.strategySimpleName());
            businessKeyPropertiesDTO.setHeader(businessKeyProperties.header());
            if(entityClass.getDeclaredField(businessKeyAttributeName)!=null){
                PropertyDescriptor pd = new PropertyDescriptor(businessKeyAttributeName, entityClass);
                Method writeMethod = pd.getWriteMethod();
                writeMethod.invoke(entity,businessKeyGeneratorManager.getNextBusinessKey(businessKeyPropertiesDTO));
            }
        }
    }
}

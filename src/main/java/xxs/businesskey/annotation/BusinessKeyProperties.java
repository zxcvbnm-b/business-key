package xxs.businesskey.annotation;
import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessKeyProperties {
   String businessKeyName() ;/*资源*/
   String businessKeyAttributeName() ;/*业务属性名称*/
   String strategySimpleName() default "" ;/*使用的业务主键生成策略类名称*/
   String header() default "" ;/*头部*/
}

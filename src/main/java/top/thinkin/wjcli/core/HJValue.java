package top.thinkin.wjcli.core;

/**
 * Created by frost on 18/1/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface HJValue {
    public String name();
    public boolean req() default false;
    public String help() default "";
    public String ext() default "";
}

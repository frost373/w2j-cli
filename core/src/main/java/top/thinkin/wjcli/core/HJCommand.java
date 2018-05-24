package top.thinkin.wjcli.core;

/**
 * Created by frost on 18/1/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HJCommand {
    public String name();
    public String help() default "";
    public boolean ask() default false;
    public String ext() default "";
}

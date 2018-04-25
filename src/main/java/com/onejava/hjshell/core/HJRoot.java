package com.onejava.hjshell.core;

/**
 * Created by frost on 18/1/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HJRoot {
    public String name();
    public String help();
    public String ext() default "";
}

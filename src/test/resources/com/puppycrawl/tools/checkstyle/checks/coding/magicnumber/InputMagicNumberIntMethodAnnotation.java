package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InputMagicNumberIntMethodAnnotation {
        int value();
}

/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

import javax.annotation.CheckForNull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@interface InputOneTopLevelClassAnnotation2A { // violation 'Top-level class InputOneTopLevelClassAnnotation2A has to reside in its own source file.'
   String author();
   String date();
}

@CheckForNull // violation 'Top-level class InputOneTopLevelClassAnnotation2B has to reside in its own source file.'
@TypeQualifierDefault(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface InputOneTopLevelClassAnnotation2B {
   String author();
   int currentRevision() default 1;
}

public @interface InputOneTopLevelClassAnnotation2 {
   String author();
   String date();
}

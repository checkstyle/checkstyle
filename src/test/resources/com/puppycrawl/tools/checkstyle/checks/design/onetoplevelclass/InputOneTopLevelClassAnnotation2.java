/*
OneTopLevelClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

import javax.annotation.CheckForNull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// violation below 'Top-level class Input.* has to reside in its own source file.'
@interface InputOneTopLevelClassAnnotation2A {
   String author();
   String date();
}

// violation below 'Top-level class Input.* has to reside in its own source file.'
@CheckForNull
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

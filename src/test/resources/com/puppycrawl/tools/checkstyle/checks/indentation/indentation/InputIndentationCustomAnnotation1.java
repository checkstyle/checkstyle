package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.lang.annotation.ElementType; //indent:0 exp:0
import java.lang.annotation.Retention; //indent:0 exp:0
import java.lang.annotation.RetentionPolicy; //indent:0 exp:0
import java.lang.annotation.Target; //indent:0 exp:0

public @interface InputIndentationCustomAnnotation1 { //indent:0 exp:0
    int value = 1; //indent:4 exp:4
    int value() default 0; //indent:4 exp:4
} //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME) //indent:0 exp:0
@interface Multitude { String value(); } //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME) //indent:0 exp:0
@interface MetaConfig { //indent:0 exp:0

	static class DevConfig { //indent:4 exp:4
	} //indent:4 exp:4

	static class ProductionConfig { //indent:4 exp:4
	} //indent:4 exp:4
	Class<?>[] classes() default { DevConfig.class, ProductionConfig.class }; //indent:4 exp:4
} //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME) //indent:0 exp:0
@interface Multitudes { Multitude[] value(); } //indent:0 exp:0

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME) //indent:0 exp:0
@interface Aclass { //indent:0 exp:0
    String b(); //indent:4 exp:4
    String[] c(); //indent:4 exp:4
} //indent:0 exp:0
class InnerAnnotSingleLine2 { //indent:0 exp:0
     @Retention(RetentionPolicy.SOURCE) @interface AnnotationOneLine {} //indent:5 exp:4 warn
} //indent:0 exp:0
@interface RepeatableInner4 { //indent:0 exp:0
    public AnnotationWithTarget.AnnotationInnerLineWrap[] value(); //indent:4 exp:4
} //indent:0 exp:0
@interface HelloAnnot { //indent:0 exp:0
    public //indent:4 exp:4
        String myString() //indent:8 exp:8
        default "Hello"; //indent:8 exp:8
} //indent:0 exp:0
@ //indent:0 exp:0
 interface //indent:1 exp:0 warn
MyAnnotationWrapped { //indent:0 exp:0
    int value() default -1; //indent:4 exp:4
    AnnotationWithTarget.AnnotationInnerLineWrap field(); //indent:4 exp:4
} //indent:0 exp:0
@Retention(RetentionPolicy.RUNTIME) //indent:0 exp:0
           @interface Marker {} //indent:11 exp:0 warn
@Target({ ElementType.METHOD, ElementType.TYPE_PARAMETER }) //indent:0 exp:0
@Retention(RetentionPolicy.RUNTIME) //indent:0 exp:0
				@interface FetchProfile { //indent:16 exp:0 warn
	String name(); //indent:4 exp:4

	FetchOverride[] fetchOverrides(); //indent:4 exp:4

	@Target({ ElementType.TYPE, ElementType.METHOD }) //indent:4 exp:4
	@Retention(RetentionPolicy.RUNTIME) //indent:4 exp:4
			@interface FetchOverride { //indent:12 exp:4 warn
		String association(); //indent:8 exp:8
	} //indent:4 exp:4
} //indent:0 exp:0
				class FetchProfile2 { //indent:16 exp:0 warn
	String name; //indent:4 exp:4

	FetchOverride2[] fetchOverrides; //indent:4 exp:4

	@Target({ ElementType.TYPE, ElementType.METHOD }) //indent:4 exp:4
    @interface FetchOverride2 { //indent:4 exp:4
		String association(); //indent:8 exp:8
	} //indent:4 exp:4
} //indent:0 exp:0

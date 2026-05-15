package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

import java.lang.annotation.Target;                                        //indent:0 exp:0
import java.lang.annotation.Retention;                                     //indent:0 exp:0
import java.lang.annotation.RetentionPolicy;                               //indent:0 exp:0
import java.lang.annotation.ElementType;                                   //indent:0 exp:0

@interface AlphaChars {}                                                   //indent:0 exp:0
@interface NumericChars {}                                                 //indent:0 exp:0
@interface Chars {                                                         //indent:0 exp:0
  char[] value();                                                          //indent:2 exp:2
}                                                                          //indent:0 exp:0
public class InputIndentationClassDefWrappedModifiers {                    //indent:0 exp:0
  public class ValidClass {}                                               //indent:2 exp:2
  public                                                                   //indent:2 exp:2
  class WrappedClass {}                                                    //indent:2 exp:6 warn
  public                                                                   //indent:2 exp:2
  static class WrappedStatic {}                                            //indent:2 exp:6 warn
  public                                                                   //indent:2 exp:2
      static class CorrectlyWrappedStatic {}                               //indent:6 exp:6
  public                                                                   //indent:2 exp:2
  static                                                                   //indent:2 exp:6 warn
      final class WrappedStaticFinal {}                                    //indent:6 exp:6
  public                                                                   //indent:2 exp:2
      static                                                               //indent:6 exp:6
      final class CorrectlyWrappedStaticFinal {}                           //indent:6 exp:6
  public @interface InlineAnnotation {}                                    //indent:2 exp:2
  public                                                                   //indent:2 exp:2
      strictfp class WrappedStrictfp {}                                    //indent:6 exp:6
  public                                                                   //indent:2 exp:2
  strictfp class CorrectlyWrappedStrictfp {}                               //indent:2 exp:6 warn
  public                                                                   //indent:2 exp:2
      @AlphaChars class WrappedAnnotation {}                               //indent:6 exp:6
  public                                                                   //indent:2 exp:2
  @AlphaChars class CorrectlyWrappedAnnotation {}                          //indent:2 exp:6 warn
  public                                                                   //indent:2 exp:2
      strictfp                                                             //indent:6 exp:6
      @AlphaChars                                                          //indent:6 exp:6
      static class WrappedMultiple {}                                      //indent:6 exp:6
  public                                                                   //indent:2 exp:2
  static                                                                   //indent:2 exp:6 warn
  strictfp class C5{}                                                      //indent:2 exp:6 warn
  public static                                                            //indent:2 exp:2
      class WrapAfterStatic {};                                            //indent:6 exp:6
  public static                                                            //indent:2 exp:2
      final class WrapAfterStaticAndBeforeFinal {};                        //indent:6 exp:6
  public                                                                   //indent:2 exp:2
      static                                                               //indent:6 exp:6
          final                                                            //indent:10 exp:10
              class WrapBeforeStaticAndAfterFinal {};                      //indent:14 exp:14
  public                                                                   //indent:2 exp:2
      static                                                               //indent:6 exp:6
          final class WrapBeforeStaticAndBeforeFinal {};                   //indent:10 exp:10
  public @X class NoWrapWithAnnotation {};                                 //indent:2 exp:2
  public @X @Y @Z class NoWrapWithManyAnnotations {};                      //indent:2 exp:2
  public                                                                   //indent:2 exp:2
      @X @Y @Z class ManyAnnotationsWrapped {};                            //indent:6 exp:6
  public @X                                                                //indent:2 exp:2
      @Y @Z class OneWrapWithManyAnnotations {};                           //indent:6 exp:6
  public @X                                                                //indent:2 exp:2
      @Y                                                                   //indent:6 exp:6
        @Z class ManyAnnotationsWithManyWraps {};                          //indent:8 exp:8
  public static @X @Y final class NoWrap2 {};                              //indent:2 exp:2
  public static                                                            //indent:2 exp:2
      @X @Y final class  WrapBetweenStaticAndAnnotation {};                //indent:6 exp:6
  public static @X @Y                                                      //indent:2 exp:2
      final class WrapBetweenAnnotationAndFinal {};                        //indent:6 exp:6
  public static @X                                                         //indent:2 exp:2
      @Y final                                                             //indent:6 exp:6
          class ManyWrap2 {};                                              //indent:10 exp:10
  public                                                                   //indent:2 exp:2
      static @X @Y                                                         //indent:6 exp:6
        final class ManyWraps3 {};                                         //indent:8 exp:8
}                                                                          //indent:0 exp:0
  @interface X {};                                                         //indent:2 exp:2
  @interface Y {};                                                         //indent:2 exp:2
  @interface Z {};                                                         //indent:2 exp:2
class StandardAuthorizerPropertyTest {                                     //indent:0 exp:0
  @Target(ElementType.ANNOTATION_TYPE)                                     //indent:2 exp:2
  @Retention(RetentionPolicy.RUNTIME)                                      //indent:2 exp:2
  @AlphaChars @NumericChars @Chars({ '_', '-', '.' })                      //indent:2 exp:2
  public @interface ValidTopicChars {}                                     //indent:2 exp:2
}                                                                          //indent:0 exp:0

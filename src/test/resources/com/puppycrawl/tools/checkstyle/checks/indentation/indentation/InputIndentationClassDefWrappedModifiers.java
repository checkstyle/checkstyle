package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

public class InputIndentationClassDefWrappedModifiers {                  //indent:0 exp:0
  public class ValidClass {}                                             //indent:2 exp:2
  public                                                                 //indent:2 exp:2
  class WrappedClass {}                                                  //indent:2 exp:6 warn
  public                                                                 //indent:2 exp:2
  static class WrappedStatic {}                                          //indent:2 exp:6 warn
  public                                                                 //indent:2 exp:2
      static class CorrectlyWrappedStatic {}                             //indent:6 exp:6
  public                                                                 //indent:2 exp:2
  static                                                                 //indent:2 exp:6 warn
      final class WrappedStaticFinal {}                                  //indent:6 exp:6
  public                                                                 //indent:2 exp:2
      static                                                             //indent:6 exp:6
      final class CorrectlyWrappedStaticFinal {}                         //indent:6 exp:6
  public @interface InlineAnnotation {}                                  //indent:2 exp:2
}                                                                        //indent:0 exp:0

class StandardAuthorizerPropertyTest {                                   //indent:0 exp:0
  @Target(ElementType.ANNOTATION_TYPE)                                   //indent:2 exp:2
  @Retention(RetentionPolicy.RUNTIME)                                    //indent:2 exp:2
  @AlphaChars @NumericChars @Chars({ '_', '-', '.' })                    //indent:2 exp:2
  public @interface ValidTopicChars {}                                   //indent:2 exp:2
}                                                                        //indent:0 exp:0

/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.lang.annotation.ElementType; //indent:0 exp:0
import java.lang.annotation.Target; //indent:0 exp:0

import org.junit.Ignore; //indent:0 exp:0
import org.junit.runners.Suite; //indent:0 exp:0
import org.junit.Test; //indent:0 exp:0

@Ignore //indent:0 exp:0
public class InputIndentationInvalidAnnotationIndentStrict //indent:0 exp:0
    implements Comparable<InputIndentationInvalidAnnotationIndentStrict> { //indent:4 exp:4

  @Override //indent:2 exp:2
public int compareTo( //indent:0 exp:2 warn
      final InputIndentationInvalidAnnotationIndentStrict otherInput) { //indent:6 exp:6
    return 0; //indent:4 exp:4
  } //indent:2 exp:2

  @Ignore //indent:2 exp:2
      public void e() { //indent:6 exp:2 warn
  } //indent:2 exp:2

  void f() { //indent:2 exp:2
    Object b = new Thread(new Runnable() { //indent:4 exp:4
      @Override //indent:6 exp:6
public void run() { //indent:0 exp:6 warn
        throw new RuntimeException(); //indent:8 exp:8
      } //indent:6 exp:6
    }); //indent:4 exp:4
  } //indent:2 exp:2

  @Test( //indent:2 exp:2
      timeout = 42L, //indent:6 exp:6
      expected = Exception.class //indent:6 exp:6
  ) //indent:2 exp:2
  @Ignore //indent:2 exp:2
  public void g() { //indent:2 exp:2
    e(); //indent:4 exp:4
  } //indent:2 exp:2

  @Ignore //indent:2 exp:2
  @Test( //indent:2 exp:2
      timeout = 42L, //indent:6 exp:6
      expected = Exception.class //indent:6 exp:6
  ) //indent:2 exp:2
  public void h() { //indent:2 exp:2
    e(); //indent:4 exp:4
  } //indent:2 exp:2

  @Ignore //indent:2 exp:2
  @Test public void i() { //indent:2 exp:2
    e(); //indent:4 exp:4
  } //indent:2 exp:2

  @Ignore //indent:2 exp:2
  public static final //indent:2 exp:2
      void //indent:6 exp:6
      veryLooooooooooooooooooooooooooooooooooooooooooooongMethodName() { //indent:6 exp:6
    throw new RuntimeException(); //indent:4 exp:4
  } //indent:2 exp:2

   @Ignore //indent:3 exp:2 warn
  public void j() { //indent:2 exp:2
    e(); //indent:4 exp:4
  } //indent:2 exp:2

    @Deprecated //indent:4 exp:2 warn
    @AnnotationIndentationAnnotationStrict2( //indent:4 exp:2 warn
        @AnnotationIndentationAnnotationStrict3) //indent:8 exp:6 warn
  @AnnotationIndentationAnnotationStrict3 //indent:2 exp:2
  private String attributeWithAnnotations; //indent:2 exp:2
} //indent:0 exp:0

@Ignore //indent:0 exp:0
@Suite.SuiteClasses({ //indent:0 exp:0
    StrictAnnotatedClass.class, //indent:4 exp:4
    StrictAnnotatedClass2.class //indent:4 exp:4
}) //indent:0 exp:0
class StrictAnnotatedClass { //indent:0 exp:0
  public void method() { //indent:2 exp:2
    throw new RuntimeException(); //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0

@Ignore //indent:0 exp:0
  class StrictAnnotatedClass2 { //indent:2 exp:0 warn
  public void method() { //indent:2 exp:2
    throw new RuntimeException(); //indent:4 exp:4
  } //indent:2 exp:2

  @Test //indent:2 exp:2
  public void testNoDestfileSpecified(@AnnotationIndentationAnnotation3 String a, //indent:2 exp:2
      @AnnotationIndentationAnnotation3 String b, //indent:6 exp:6
        @AnnotationIndentationAnnotation3 String c) { //indent:8 exp:6 warn
    @AnnotationIndentationAnnotation3 //indent:4 exp:4
    String x = ( //indent:4 exp:4
        @AnnotationIndentationAnnotation3 String) //indent:8 exp:8
        b + c; //indent:8 exp:8
    @AnnotationIndentationAnnotation3 String y = x; //indent:4 exp:4
    throw new //indent:4 exp:4
      @AnnotationIndentationAnnotation3 //indent:6 exp:6
      RuntimeException(); //indent:6 exp:6
  } //indent:2 exp:2
} //indent:0 exp:0

@AnnotationIndentationAnnotationStrict1( //indent:0 exp:0
    @AnnotationIndentationAnnotationStrict2( //indent:4 exp:4
        @AnnotationIndentationAnnotationStrict3 //indent:8 exp:4 warn
    ) //indent:4 exp:0 warn
) //indent:0 exp:0
class StrictAnnotatedClass3 { //indent:0 exp:0
} //indent:0 exp:0

@Target(ElementType.TYPE_USE) //indent:0 exp:0
@interface //indent:0 exp:0
AnnotationIndentationAnnotationStrict1 { //indent:0 exp:0
	AnnotationIndentationAnnotationStrict2[] value(); //indent:4 exp:4
} //indent:0 exp:0
@interface //indent:0 exp:0
  AnnotationIndentationAnnotationStrict2 { //indent:2 exp:0 warn
	AnnotationIndentationAnnotationStrict3[] value(); //indent:4 exp:4
} //indent:0 exp:0
@Target(ElementType.TYPE_USE) //indent:0 exp:0
@interface AnnotationIndentationAnnotationStrict3 {} //indent:0 exp:0

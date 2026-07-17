/* Config:                                                                 //indent:0 exp:0
 * basicOffset = 2                                                         //indent:1 exp:1
 * braceAdjustment = 2                                                     //indent:1 exp:1
 * caseIndent = 2                                                          //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 * tabWidth = 4                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                     //indent:1 exp:1
 */                                                                        //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

@Deprecated                                                                //indent:0 exp:0
public class InputIndentationAnnotationBeforeClass {                       //indent:0 exp:0
  @SuppressWarnings("try")                                                 //indent:2 exp:2
  public static class TestBatchSource implements Runnable {                //indent:2 exp:2
    @Override                                                              //indent:4 exp:4
    public void run() { }                                                  //indent:4 exp:4
    @SuppressWarnings("unused")                                            //indent:4 exp:4
    public static int count;                                               //indent:4 exp:4
  }                                                                        //indent:2 exp:2
  @SuppressWarnings("try")                                                 //indent:2 exp:2
  public static class TestBatchSourceFail extends TestBatchSource {        //indent:2 exp:2
  }                                                                        //indent:2 exp:2
  @Deprecated                                                              //indent:2 exp:2
  public class InnerConfig {                                               //indent:2 exp:2
    @Deprecated                                                            //indent:4 exp:4
    protected static class ReportingAdminOnlyConfiguration {               //indent:4 exp:4
      @Deprecated                                                          //indent:6 exp:6
      public Object stubResource() {                                       //indent:6 exp:6
        return new Object();                                               //indent:8 exp:8
      }                                                                    //indent:6 exp:6
    }                                                                      //indent:4 exp:4
  }                                                                        //indent:2 exp:2
  @Deprecated                                                              //indent:2 exp:2
  protected static class ReportingAdminOnlyConfig {                        //indent:2 exp:2
    @Deprecated                                                            //indent:4 exp:4
    public Object stubResource() {                                         //indent:4 exp:4
      return new Object();                                                 //indent:6 exp:6
    }                                                                      //indent:4 exp:4
  }                                                                        //indent:2 exp:2
 @Deprecated                                                               //indent:1 exp:2 warn
  public static class AnnotationWrappedWrongIndent {                       //indent:2 exp:2
  }                                                                        //indent:2 exp:2
  @SuppressWarnings("deprecation")                                         //indent:2 exp:2
  private static final class TlsMasterSecretKey {}                         //indent:2 exp:2
 @SuppressWarnings("bar")                                                  //indent:1 exp:2 warn
 private static final class SameWrongCol {}                                //indent:1 exp:2 warn
    @SuppressWarnings("deprecation")                                       //indent:4 exp:2 warn
    private static final class TlsMasterSecretKey2 {}                      //indent:4 exp:2 warn
}                                                                          //indent:0 exp:0

/*
Indentation
arrayInitIndent = 2
basicOffset = 2
braceAdjustment = 2
caseIndent = 2
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4

*/
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
 // violation below ''class def modifier' has incorrect indentation level 1, expected level should be 2.'
 @Deprecated                                                               //indent:1 exp:2 warn
  public static class AnnotationWrappedWrongIndent {                       //indent:2 exp:2
  }                                                                        //indent:2 exp:2
  @SuppressWarnings("deprecation")                                         //indent:2 exp:2
  private static final class TlsMasterSecretKey {}                         //indent:2 exp:2
 // violation 2 lines below ''class def modifier' has incorrect indentation level 1, expected level should be 2.'
 // violation 2 lines below ''class def modifier' has incorrect indentation level 1, expected level should be 2.'
 @SuppressWarnings("bar")                                                  //indent:1 exp:2 warn
 private static final class SameWrongCol {}                                //indent:1 exp:2 warn
    // violation 2 lines below ''class def modifier' has incorrect indentation level 4, expected level should be 2.'
    // violation 2 lines below ''class def modifier' has incorrect indentation level 4, expected level should be 2.'
    @SuppressWarnings("deprecation")                                       //indent:4 exp:2 warn
    private static final class TlsMasterSecretKey2 {}                      //indent:4 exp:2 warn
}                                                                          //indent:0 exp:0

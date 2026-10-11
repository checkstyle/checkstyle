/* Config:                                                                 //indent:0 exp:0
 * basicOffset = 4                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 4                                                          //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 * tabWidth = 4                                                            //indent:1 exp:1
 */                                                                        //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

public class InputIndentationAnnotBeforeClassOpenJdkCase {                 //indent:0 exp:0
   @SuppressWarnings("deprecation")                                        //indent:3 exp:4 warn
   private static final class TlsMasterSecretKey {}                        //indent:3 exp:4 warn

        @SuppressWarnings("serial")                                        //indent:8 exp:4 warn
        private static final class ResizeIcon {}                           //indent:8 exp:4 warn

  @Deprecated                                                              //indent:2 exp:4 warn
  public static class WrongIndentSingle {}                                 //indent:2 exp:4 warn

      @Deprecated                                                          //indent:6 exp:4 warn
      public static class WrongIndentOver {}                               //indent:6 exp:4 warn

   @SuppressWarnings("foo")                                                //indent:3 exp:4 warn
   @Deprecated                                                             //indent:3 exp:4
   private static class TwoAnnotationsWrong {}                             //indent:3 exp:4 warn

      @Deprecated                                                          //indent:6 exp:4 warn
      @SuppressWarnings("x")                                               //indent:6 exp:4
      public final class TwoAnnotationsOver {}                             //indent:6 exp:4 warn

  @Deprecated                                                              //indent:2 exp:4 warn
  protected static class WrongProtected {}                                 //indent:2 exp:4 warn

   @SuppressWarnings("unchecked")                                          //indent:3 exp:4 warn
   public enum WrongEnum {}                                                //indent:3 exp:4 warn

   @SuppressWarnings("unchecked")                                          //indent:3 exp:4 warn
   public interface WrongInterface {}                                      //indent:3 exp:4 warn
}                                                                          //indent:0 exp:0

/*
Indentation
arrayInitIndent = 2
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

public class InputIndentationAnnotBeforeClassOpenJdkCase {                 //indent:0 exp:0
   // violation 2 lines below ''class def modifier' has incorrect indentation level 3, expected level should be 4.'
   // violation 2 lines below ''class def modifier' has incorrect indentation level 3, expected level should be 4.'
   @SuppressWarnings("deprecation")                                        //indent:3 exp:4 warn
   private static final class TlsMasterSecretKey {}                        //indent:3 exp:4 warn

        // violation 2 lines below ''class def modifier' has incorrect indentation level 8, expected level should be 4.'
        // violation 2 lines below ''class def modifier' has incorrect indentation level 8, expected level should be 4.'
        @SuppressWarnings("serial")                                        //indent:8 exp:4 warn
        private static final class ResizeIcon {}                           //indent:8 exp:4 warn

  // violation 2 lines below ''class def modifier' has incorrect indentation level 2, expected level should be 4.'
  // violation 2 lines below ''class def modifier' has incorrect indentation level 2, expected level should be 4.'
  @Deprecated                                                              //indent:2 exp:4 warn
  public static class WrongIndentSingle {}                                 //indent:2 exp:4 warn

      // violation 2 lines below ''class def modifier' has incorrect indentation level 6, expected level should be 4.'
      // violation 2 lines below ''class def modifier' has incorrect indentation level 6, expected level should be 4.'
      @Deprecated                                                          //indent:6 exp:4 warn
      public static class WrongIndentOver {}                               //indent:6 exp:4 warn

   // violation 2 lines below ''class def modifier' has incorrect indentation level 3, expected level should be 4.'
   // violation 3 lines below ''class def modifier' has incorrect indentation level 3, expected level should be 4.'
   @SuppressWarnings("foo")                                                //indent:3 exp:4 warn
   @Deprecated                                                             //indent:3 exp:4
   private static class TwoAnnotationsWrong {}                             //indent:3 exp:4 warn

      // violation 2 lines below ''class def modifier' has incorrect indentation level 6, expected level should be 4.'
      // violation 3 lines below ''class def modifier' has incorrect indentation level 6, expected level should be 4.'
      @Deprecated                                                          //indent:6 exp:4 warn
      @SuppressWarnings("x")                                               //indent:6 exp:4
      public final class TwoAnnotationsOver {}                             //indent:6 exp:4 warn

  // violation 2 lines below ''class def modifier' has incorrect indentation level 2, expected level should be 4.'
  // violation 2 lines below ''class def modifier' has incorrect indentation level 2, expected level should be 4.'
  @Deprecated                                                              //indent:2 exp:4 warn
  protected static class WrongProtected {}                                 //indent:2 exp:4 warn

   // violation 2 lines below ''enum def modifier' has incorrect indentation level 3, expected level should be 4.'
   // violation 2 lines below ''enum def modifier' has incorrect indentation level 3, expected level should be 4.'
   @SuppressWarnings("unchecked")                                          //indent:3 exp:4 warn
   public enum WrongEnum {}                                                //indent:3 exp:4 warn

   // violation 2 lines below ''interface def modifier' has incorrect indentation level 3, expected level should be 4.'
   // violation 2 lines below ''interface def modifier' has incorrect indentation level 3, expected level should be 4.'
   @SuppressWarnings("unchecked")                                          //indent:3 exp:4 warn
   public interface WrongInterface {}                                      //indent:3 exp:4 warn
}                                                                          //indent:0 exp:0

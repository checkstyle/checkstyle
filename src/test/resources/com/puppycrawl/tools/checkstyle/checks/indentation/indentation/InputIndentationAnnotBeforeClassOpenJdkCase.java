package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationAnnotBeforeClassOpenJdkCase {
   @SuppressWarnings("deprecation")                                        //indent:3 exp:4
   private static final class TlsMasterSecretKey {}                        //indent:3 exp:4 warn

        @SuppressWarnings("serial")                                        //indent:8 exp:4
        private static final class ResizeIcon {}                           //indent:8 exp:4 warn

  @Deprecated                                                              //indent:2 exp:4
  public static class WrongIndentSingle {}                                 //indent:2 exp:4 warn

      @Deprecated                                                          //indent:6 exp:4
      public static class WrongIndentOver {}                               //indent:6 exp:4 warn

   @SuppressWarnings("foo")                                                //indent:3 exp:4
   @SuppressWarnings("bar")                                                //indent:3 exp:4
   private static class TwoAnnotationsWrong {}                             //indent:3 exp:4 warn

      @Deprecated                                                          //indent:6 exp:4
      @SuppressWarnings("x")                                               //indent:6 exp:4
      public final class TwoAnnotationsOver {}                             //indent:6 exp:4 warn

  @Deprecated                                                              //indent:2 exp:4
  protected static class WrongProtected {}                                 //indent:2 exp:4 warn

   @SuppressWarnings("unchecked")                                          //indent:3 exp:4
   public enum WrongEnum {}                                                //indent:3 exp:4 warn

   @SuppressWarnings("unchecked")                                          //indent:3 exp:4
   public interface WrongInterface {}                                      //indent:3 exp:4 warn
}

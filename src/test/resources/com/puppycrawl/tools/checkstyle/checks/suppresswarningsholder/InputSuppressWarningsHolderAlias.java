package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderAlias {

     private static final int a = 0; // violation 'invalid pattern'

     @SuppressWarnings("constantnamecheck=someAlias") // filtered violation 'invalid pattern'
     private static final int b = 0;

     @SuppressWarnings("ConstantName=SomeAlias") // filtered violation 'invalid pattern'
     private static final int c = 0;

     @SuppressWarnings("ConstantName=SomeAlias") // filtered violation 'invalid pattern'
     private static final int d = 0;

     @SuppressWarnings("constantname") // filtered violation 'invalid pattern'
     private static final int e = 0;

     @SuppressWarnings("cOnStAnTnAmEcHeCk") // filtered violation 'invalid pattern'
     private static final int f = 0;

     @SuppressWarnings("cOnStAnTnAmE") // filtered violation 'invalid pattern'
     private static final int g = 0;

}

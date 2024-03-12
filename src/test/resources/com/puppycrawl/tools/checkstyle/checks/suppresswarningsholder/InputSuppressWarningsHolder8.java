/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = ^[a-z][a-zA-Z0-9]*$


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder8 {

   // violation below 'Name 'K' must match pattern'
   private int K;   @SuppressWarnings("membername")
   private int J; // violation suppressed
}

/*
DescendantToken
limitedTokens = EMPTY_STAT
minimumDepth = (default)0
maximumDepth = 0
minimumNumber = (default)0
maximumNumber = 0
sumTokenCounts = (default)false
minimumMessage = (default)null
maximumMessage = Empty statement.
tokens = EMPTY_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenEmptyStatement
{
   public InputDescendantTokenEmptyStatement()
   {
      ; // violation
   }

   public void EmptyMethod()
   {
      ; // violation
   }

   public void EmptyStatements(boolean cond)
   {
      for (;cond;); // violation

      for (;cond;)
      {
         ; // violation
      }

      if (true); // violation

      if (true)
      {
         ; // violation
      }

      if (cond)
      {
         int i;
      }

      else
      {
         ; // violation
      }

      switch (1)
      {
         case 1 :
            ; // violation
         default :
            ; // violation
      }

      while (cond); // violation

      while (cond)
      {
         ; // violation
      }

      do; // violation
      while (cond);

      do
      {
         ; // violation
      }
      while (cond);

      try
      {
         ; // violation
      }
      catch (Exception ex)
      {
         ; // violation
      }
      finally
      {
         ; // violation
      }
   }
}

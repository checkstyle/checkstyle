/*
DescendantToken
limitedTokens = (EMPTY_STAT
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
      ;
   }

   public void EmptyStatements(boolean cond)
   {
      for (;cond;);

      for (;cond;)
      {
         ;
      }

      if (true);

      if (true)
      {
         ;
      }

      if (cond)
      {
         int i;
      }

      else
      {
         ;
      }

      switch (1)
      {
         case 1 :
            ;
         default :
            ;
      }

      while (cond);

      while (cond)
      {
         ;
      }

      do;
      while (cond);

      do
      {
         ;
      }
      while (cond);

      try
      {
         ;
      }
      catch (Exception ex)
      {
         ;
      }
      finally
      {
         ;
      }
   }
}

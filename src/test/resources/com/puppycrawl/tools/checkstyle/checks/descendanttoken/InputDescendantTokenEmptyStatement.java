/*
DescendantToken
limitedTokens = EMPTY_STAT
maximumDepth = 0
maximumMessage = Empty statement.
maximumNumber = 0
minimumDepth = (default)0
minimumMessage = (default)null
minimumNumber = (default)0
sumTokenCounts = (default)false
tokens = EMPTY_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

public class InputDescendantTokenEmptyStatement
{
   public InputDescendantTokenEmptyStatement()
   {
      ; // violation 'Empty statement'
   }

   public void EmptyMethod()
   {
      ; // violation 'Empty statement'
   }

   public void EmptyStatements(boolean cond)
   {
      for (;cond;); // violation 'Empty statement'

      for (;cond;)
      {
         ; // violation 'Empty statement'
      }

      if (true); // violation 'Empty statement'

      if (true)
      {
         ; // violation 'Empty statement'
      }

      if (cond)
      {
         int i;
      }

      else
      {
         ; // violation 'Empty statement'
      }

      switch (1)
      {
         case 1 :
            ; // violation 'Empty statement'
         default :
            ; // violation 'Empty statement'
      }

      while (cond); // violation 'Empty statement'

      while (cond)
      {
         ; // violation 'Empty statement'
      }

      do; // violation 'Empty statement'
      while (cond);

      do
      {
         ; // violation 'Empty statement'
      }
      while (cond);

      try
      {
         ; // violation 'Empty statement'
      }
      catch (Exception ex)
      {
         ; // violation 'Empty statement'
      }
      finally
      {
         ; // violation 'Empty statement'
      }
   }
}

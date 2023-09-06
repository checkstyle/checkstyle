/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

/**
 * Input class for testing EmptyStatementCheck
 * @author Rick Giles
 * @version 5-May-2003
 */
public class InputEmptyStatement
{
   public InputEmptyStatement()
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

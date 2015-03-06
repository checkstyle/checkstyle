package com.puppycrawl.tools.checkstyle;

/**
 * Input class for testing EmptyStatementCheck
 * @author Rick Giles
 * @version 5-May-2003
 */
public class InputEmptyStatement
{
   public InputEmptyStatement()
   {
      ;
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

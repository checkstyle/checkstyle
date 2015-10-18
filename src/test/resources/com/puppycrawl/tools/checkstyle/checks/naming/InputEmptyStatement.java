package com.puppycrawl.tools.checkstyle.checks.naming;

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

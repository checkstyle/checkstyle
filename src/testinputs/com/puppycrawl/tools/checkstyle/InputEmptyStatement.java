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

   public void EmptyStatements()
   {
      for (;;);

      for (;;)
      {
         ;
      }

      if (true);

      if (true)
      {
         ;
      }

      if (true)
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

      while (true);

      while (true)
      {
         ;
      }

      do;
      while (true);

      do
      {
         ;
      }
      while (true);

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

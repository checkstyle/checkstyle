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
   ; // violation 'Empty statement'
   ; // violation 'Empty statement'

   public InputEmptyStatement()
   {
      ; // violation 'Empty statement'
   }

   ; // violation 'Empty statement'

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

   private int field;; // violation 'Empty statement'
   static {}; // violation 'Empty statement'
   {}; // violation 'Empty statement'
   public void method() {}; // violation 'Empty statement'
   class Nested {}; // violation 'Empty statement'
   @interface NestedAnnotation {}; // violation 'Empty statement'

   interface NestedInterface
   {
      void abstractMethod();; // violation 'Empty statement'

      default void defaultMethod() {}; // violation 'Empty statement'
   }

   private final Object anonymous = new Object() { ; }; // violation 'Empty statement'

   public void localClass()
   {
      class Local { private int value;; } // violation 'Empty statement'
   }
}

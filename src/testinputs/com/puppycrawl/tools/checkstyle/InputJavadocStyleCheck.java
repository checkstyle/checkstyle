////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

/**
 * Test input for the JavadocStyleCheck.  This check is used to perform 
 * some additional Javadoc validations.  
 * 
 * @author Chris Stillwell
 * @version 1.0
 */
public class InputJavadocStyleCheck
{
   // This is OK. We don't flag missing javadoc.  That's left for other checks.
   private String first;
   
   /** This Javadoc is missing an ending period */
   private String second;
   
   /**
    * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck} 
    * tags to stop the scan for the end of sentence. 
    * @see Somthing
    */
   public InputJavadocStyleCheck()
   {
   }
   
   /**
    * This is ok!
    */
   private void method1()
   {
   }
   
   /**
    * This is ok?
    */
   private void method2()
   {
   }
   
   /**
    * And This is ok.<br>
    */
   private void method3()
   {
   }
   
   /**
    * This should fail even.though.there are embedded periods
    */
   private void method4()
   {
   }
   
   /**
    * Test HTML in Javadoc comment
    * <dl>
    * <dt><b>This guy is missing end of bold tag
    * <dd>The dt and dd don't require end tags.
    * </dl>
    * </td>Extra tag shouldn't be here
    * 
    * @param arg1 <code>dummy.
    */
   private void method5(int arg1)
   {
   }
   
   /**
    * Protected check <b>should fail
    */
   protected void method6()
   {
   }
   
   /**
    * Package protected check <b>should fail
    */
   void method7()
   {
   }
   
   /**
    * Public check should fail</code>
    */
   public void method8()
   {
   }
   
}

////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for correct use of braces.
 * @author Oliver Burn
 **/
class InputLeftCurlyOther
{
    /** @see test method **/
    int foo() throws InterruptedException
    {
        int x = 1;
        int a = 2;
        while (true)
        {
            try
            {
                if (x > 0)
                {
                    break;
                }
                else if (x < 0) {
                    ;
                }
                else
                {
                    break;
                }
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            {
                break;
            }
            finally
            {
                break;
            }
        }

        synchronized (this)
        {
            do
            {
                x = 2;
            } while (x == 2);
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }

        // test input for bug reported by Joe Comuzzi
        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }

    // Test static initialiser
    static
    {
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    {
        HELLO,
        GOODBYE
    };

    void method2()
    {
        boolean flag = true;
        if (flag) {
            System.out.println("heh");
            flag = !flag; } System.err.
              println("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { System.err.println("it is ok."); }
    }
}

/**
 * Test input for closing brace if that brace terminates 
 * a statement or the body of a constructor. 
 */
class FooCtor
{
	int i;
	public FooCtor()
	{
		i = 1;
	}}

/**
* Test input for closing brace if that brace terminates 
* a statement or the body of a method. 
*/
class FooMethod
{
	public void fooMethod()
	{
		int i = 1;
	}}

/**
* Test input for closing brace if that brace terminates 
* a statement or the body of a named class. 
*/
class FooInner
{
	class InnerFoo
	{
		public void fooInnerMethod ()
		{
			
		}
	}}

/**
 * False positive 
 *
 */
class Absent_CustomFieldSerializer3 {

    public static void serialize() {} //false positive. Expected nothing but was "'}' should be alone on a line." 
}

class Absent_CustomFieldSerializer4
{
    public Absent_CustomFieldSerializer4() {}
}

class EmptyClass2 {}

interface EmptyInterface3 {}

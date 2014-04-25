/*
 * InputInalidMethodIndent.java
 *
 * Created on November 11, 2002, 10:14 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;
import java.util.Arrays;
/**
 *
 * @author  jrichard
 */
public class InputInvalidMethodIndent {
    
    /** Creates a new instance of InputInalidMethodIndent */
    public InputInvalidMethodIndent() {
      }
    
    // ctor with rcurly on next line
      public InputInvalidMethodIndent(int dummy)
  {
      }
      
    // method with rcurly on same line
  public void method() {
      }

    // method with rcurly on next line
    public void method2()
    {
    }
    
    // method with a bunch of params
    public int method2(int x, int y, int w, int h) {
        return 1;
    }
    
    // params on multiple lines
    public void method2(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
    }

    // params on multiple lines
    public void method3(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
        System.getProperty("foo");
    }

    
    
    // params on multiple lines
    public void method4(int x, int y, int w, int h,
        int x1, int y1, int w1, int h1)
    {
        boolean test = true;
        if (test) {
            System.getProperty("foo");
        }
    }
    
     public 
     final
     void 
    method5()
    {
        boolean test = true;
        if (test) {
            System.getProperty("foo");
        }
    }
    
   public 
   final
   void 
     method6()
    {
        boolean test = true;
        if (test) {
            System.getProperty("foo");
        }
    }
   
    public InputInvalidMethodIndent(int dummy, int dummy2)
    {
    System.getProperty("foo");
    }
      
    void method6a()
    {
      boolean test = true;
      if (test) {
          System.getProperty("foo");
      }
      
        System.out.println("methods are: " + 
          Arrays.asList(
                new String[] {"method"}).toString());

                
        System.out.println("methods are: " + 
            Arrays.asList(
              new String[] {"method"}).toString());
                
        System.out.println("methods are: " 
          + Arrays.asList(
                new String[] {"method"}).toString());
                
        System.out.println("methods are: " 
            + Arrays.asList(
              new String[] {"method"}).toString());
                

        String blah = (String) System.getProperty(
          new String("type"));

        
        String blah1 = (String) System.getProperty(
          new String("type")
      );
        
        System.out.println("methods are: " + Arrays.asList(
            new String[] {"method"}).toString()
      );
    }

    
    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) {
    }
    
    private int myfunc3(int a, int b, int c, int d) {
        return 1;
    }
    
    private void myMethod()
    {
        myfunc2(3, 4, 5, 
          6, 7, 8, 9);
            
        myfunc2(3, 4, method2(3, 4, 5, 6) + 5, 
          6, 7, 8, 9);
        

// TODO: this is not illegal, but probably should be
//        myfunc3(11, 11, Integer.
//            getInteger("mytest").intValue(),
//            11);


        System.out.toString()
      .equals("blah");
        
     
    }
    
    private void myFunc()
      throws Exception
    {
    }

    void method7() {
        // return incorrectly indented
    return;
    }

    void method8() {
        // thow invorrectly indented
    throw new RuntimeException("");
    }

    public
int[]
    method9()
    {
        return null;
    }

    private int[] getArray() {
        return new int[] {1};
    }

    private void indexTest() {
            getArray()[0] = 2;
    }
}

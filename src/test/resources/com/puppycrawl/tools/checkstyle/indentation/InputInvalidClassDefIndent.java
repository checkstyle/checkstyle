/*
 * InputValidMethodIndent.java
 *
 * Created on November 11, 2002, 10:13 PM
 */



package com.puppycrawl.tools.checkstyle.indentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author  jrichard
 */
  public class InputInvalidClassDefIndent extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {
    

}

class InputInvalidClassDefIndentB extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener 
  {
    

  }


  class InputInvalidClassDefIndentC 
{
    

  }



class InputValidClassDefIndent2 
  extends java.awt.event.MouseAdapter 
  implements java.awt.event.MouseListener 
{

}

class InputValidClassDefIndent3
  extends java.awt.event.MouseAdapter 
    implements java.awt.event.MouseListener 
{

}

final class InputValidClassDefIndent4
    extends java.awt.event.MouseAdapter 
  implements 
  java.awt.event.MouseListener 
{

}

  final class InputValidClassDefIndent5 extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener 
  {

}

  
final class InputValidClassDefIndent5b extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener 
{

  }


class InputInvalidClassDefIndentc
  extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {
    

}
  


final class InputValidClassDefIndent6 extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {

  class foo { }
  
      class fooBS { }

    
  class foo2 { public int x; }

    
    class foo3 { 
      public 
            int x; 
    }

    class foo3b { 
        public 
          int x; 
    }

    
    class foo4 { 
      public int x; 
    }
    
    class foo4c { 
        public int x; 
      }
    
      class foo4b { 
        public int x; 
    }

      
    private void myMethod() {
      class localFoo {
            
        }
          class localFoo1 {
            
          }

        class localFoo2 {
          int x;
            
            int func() { return 3; }
        }

          new JButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        
        new JButton().addActionListener(new ActionListener() 
      {
            public void actionPerformed(ActionEvent e) {
                
            }
      });
        

        new JButton().addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e) {
                
          }
        });
      
      
        new JButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = 2;
            }
        });
        
        Object o = new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                
            }
        };

        myfunc2(10, 10, 10,
            myfunc3(11, 11,
                11, 11),
            10, 10,
            10);
          
        
    }
    
    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) {
    }
    
    private int myfunc3(int a, int b, int c, int d) {
        return 1;
    }
}


final 
class InputValidClassDefIndent4d
    extends 
        java.awt.event.MouseAdapter 
    implements 
        java.awt.event.MouseListener 
{

}

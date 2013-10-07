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
public class InputValidClassDefIndent 
    extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {
    

}

class InputValidClassDefIndent2 
    extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener 
{

}

class InputValidClassDefIndent3
    extends java.awt.event.MouseAdapter 
    implements java.awt.event.MouseListener 
{

}

final class InputValidClassDefIndent4
    extends java.awt.event.MouseAdapter 
    implements java.awt.event.MouseListener 
{

}

final 
class InputValidClassDefIndent4a
    extends java.awt.event.MouseAdapter 
    implements java.awt.event.MouseListener 
{

}

final class InputValidClassDefIndent5 extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener 
{

}

final class InputValidClassDefIndent6 extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {

    class foo { }

    
    class foo2 { public int x; }

    
    class foo3 { 
        public 
        int x; 
    }

    
    class foo4 { 
        public int x; 
    }
    
    
    private void myMethod() {
        class localFoo {
            
        }

        class localFoo2 {
            int x;
            
            int func() { return 3; }
        }

        
        // TODO: this is broken right now:
        //   1) this is both an expression and an OBJBLOCK
        //   2) methods aren't yet parsed
        //   3) only CLASSDEF is handled now, not OBJBLOCK
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

    /** The less than or equal operator. */
    public static final Operator LT_OR_EQUAL =
        new Operator(
            "<=",
            new OperatorHelper()
            {
                public boolean compare(int value1, int value2)
                {
                    return (value1 <= value2);
                }

                public boolean compare(Comparable obj1, Comparable obj2)
                {
                    return (obj1.compareTo(obj2) <= 0);
                }
            });
}

class HashingContainer<K, V> {
    @Deprecated
    public Object[] table;

    @Override
    public String toString() {
        return "";
    }
}

class Operator {
    public Operator(String str, OperatorHelper handler) {
    }
}

class OperatorHelper {
}

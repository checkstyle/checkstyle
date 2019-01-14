////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; //no violation: trailing comment
import java.io.Serializable;
import java.util.ArrayList; /*no violation: trailing comment*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Collections;
/* no violation: block comment after token*/

import com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.InputEmptyLineSeparator;
//no violation: single line comment after token

import javax.swing.AbstractAction; /* no violation: no trailing comment
*/

import org.apache.commons.beanutils.locale.converters.ByteLocaleConverter;
import org.apache.commons.beanutils.BasicDynaBean;
class InputEmptyLineSeparator
{
    public static final double FOO_PI = 3.1415;
    private boolean flag = true; 
    static {
        //empty static initializer
    }
    // no blank line - fail
    {
        //empty instance initializer
    }

    // one blank line - ok
    {
        //empty instance initializer
    }
    // no blank line - fail
    /**
     * 
     * 
     * 
     */
    private InputEmptyLineSeparator()
    {
        //empty
    }
    //separator blank line
    public int compareTo(Object aObject)
    {
        int number = 0;
        return 0;
    }

    public int compareTo2(Object aObject) // empty line - ok
    {
        int number = 0;
        return 0;
    }
    /**
     * No blank line between methods - fail
     * @param task
     * @param result
     * @return
     */
    public static <T> Callable<T> callable(Runnable task, T result)
    {
        return null;
    }

    /**
     * Blank line before Javadoc - ok
     * @param task
     * @param result
     * @return
     */
    public static <T> Callable<T> callable2(Runnable task, T result)
    {
        return null;
    }
    /**
     * Blank line after Javadoc - ok
     * @param task
     * @param result
     * @return
     */
    
    public static <T> Callable<T> callable3(Runnable task, T result)
    {
        return null;
    }

    public int getBeastNumber()
    {
        return 666;
    }
    interface IntEnum {
    }

    class InnerClass {
        
        public static final double FOO_PI_INNER = 3.1415;

        private boolean flagInner = true; 

        {
            //empty instance initializer
        }

        private InnerClass()
        {
            //empty
        }
    }


    class SecondInnerClass {

        private int intVariable;
    }
}

class Class2{
    public int compareTo(InputEmptyLineSeparator aObject) //ok
    {
        int number = 0;
        return 0;
    }
    
    Class2 anon = new Class2(){
        public int compareTo(InputEmptyLineSeparator aObject) //ok
        {
            int number = 0;
            return 0;
        }
    };
}

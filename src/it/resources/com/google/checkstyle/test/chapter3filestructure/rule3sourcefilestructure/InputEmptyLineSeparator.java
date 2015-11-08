////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.google.checkstyle.test.chapter3filestructure.rule3sourcefilestructure; //warn
import java.io.Serializable; //warn
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Collections;
import com.google.common.base.CharMatcher;
import com.google.common.io.CharSource;

import javax.swing.AbstractAction;

import org.apache.commons.beanutils.locale.converters.ByteLocaleConverter;
class InputEmptyLineSeparator //warn
{
	public static final double FOO_PI = 3.1415;
	private boolean flag = true; 
	static {  //warn
        //empty static initializer
	}

	{
		//empty instance initializer
	}

	/**
	 * 
	 * 
	 * 
	 */
	private InputEmptyLineSeparator()
	{
		//empty
	}

    public int compareTo(InputEmptyLineSeparator aObject)
    {
    	int number = 0;
        return 0;
    }
    /**
     * 
     * @param task
     * @param result
     * @return
     */
    public static <T> Callable<T> callable(Runnable task, T result) // warn
    {
        return null;
    }

    public int getBeastNumber()
    {
        return 666;
    }
    interface IntEnum { //warn
    }

    class InnerClass {
    	
    	public static final double FOO_PI_INNER = 3.1415;
    	private boolean flagInner = true; 
    	{ //warn
    		//empty instance initializer
    	}

    	private InnerClass()
    	{
    		//empty
    	}

    }
    
    class InnerClass2 { //ok
        private InnerClass2() //ok
        {
            //empty
        }
    }

    class InnerClass3 { //ok
        public int compareTo(InputEmptyLineSeparator aObject) //ok
        {
            int number = 0;
            return 0;
        }

    }
}

class Class { //ok
    private Class() {} //ok
} 
class Class2{ //warn
    public int compareTo(InputEmptyLineSeparator aObject) //ok
    {
        int number = 0;
        return 0;
    }
    Class2 anon = new Class2(){ //warn
        public int compareTo(InputEmptyLineSeparator aObject) //ok
        {
            int number = 0;
            return 0;
        }
    };
}

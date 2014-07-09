package com.puppycrawl.tools.checkstyle.whitespace;
import java.io.Serializable;
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
import org.apache.commons.io.FilenameUtils;
class InputEmptyLineSeparatorCheck
{
	public static final double FOO_PI = 3.1415;
	private boolean flag = true; 
	//separator blank line
	static {
        //empty static initializer
	}
	//separator blank line
	{
		//empty instance initializer
	}
	//separator blank line
	/**
	 * 
	 * 
	 */
	private InputEmptyLineSeparatorCheck()
	{
		//empty
	}
	//separator blank line
    public int compareTo(InputGenericWhitespaceCheck aObject)
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
    public static <T> Callable<T> callable(Runnable task, T result)
    {
        return null;
    }
    //separator blank line
    public int getBeastNumber()
    {
        return 666;
    }
    interface IntEnum {
    }
    //separator blank line
    class InnerClass {
    	
    	public static final double FOO_PI_INNER = 3.1415;
    	//separator blank line
    	private boolean flagInner = true; 
    	//separator blank line
    	static {
            //empty static initializer
    	}
    	//separator blank line
    	{
    		//empty instance initializer
    	}
    	//separator blank line
    	private InnerClass()
    	{
    		//empty
    	}
    }
}

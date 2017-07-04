////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: Feb-2001
// Ignore error
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

class InputLocalVariableNameOneCharInitVarName
{
	public void fooMethod()
	{
		for(int i = 1; i <10; i++) {
			//come code
		}
		
		int i = 0;
		
        for(int index = 1; index < 10; index++) {
			//come code
		}
        
        for(int Index = 1; Index < 10; Index++) {
			//come code
		}
        
        int index = 1;

		for(; index < 10; index++) {
			//come code
		}
		
		for(; i < 12; i++) {
			//come code
		}
		
		Map<String, String> map = new HashMap<String, String>();
		
		for (Map.Entry<String, String> e : map.entrySet()) {
			//some code
		}
	}
}

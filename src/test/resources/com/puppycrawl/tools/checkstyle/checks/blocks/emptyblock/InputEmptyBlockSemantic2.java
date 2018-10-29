////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

class InputEmptyBlockSemantic2
{
	public void fooMethod()
	{
		int a = 1;
		if (a == 1) {} //is not OK 
		char[] s = {'1', '2'};
		int index = 2;
		if (doSideEffect() == 1) {} //is not OK, 
		while ((a = index - 1) != 0) {} // is OK 
		for (; index < s.length && s[index] != 'x'; index++) {} // is OK
		if (a == 1) {} else {System.identityHashCode("a");} // is not OK
		switch (a) {} //warn
		switch (a) { //ok
        case 1:
            a = 2;
        case 2:
            a = 3;
        default:
            a = 0;
        }
	}
	
	public int doSideEffect()
	{
		return 1;
	}
}

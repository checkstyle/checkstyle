/* Config:                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:         //indent:1 exp:1
 * tabWidth = 8                                                                     //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * braceAdjustment = 0                                                              //indent:1 exp:1
 * caseIndent = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                                 //indent:1 exp:1
 * forceStrictCondition = false                                                     //indent:1 exp:1
 * lineWrappingIndentation = 4                                                      //indent:1 exp:1
 */                                                                                 //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Function; //indent:0 exp:0

enum InputIndentationLambdaInEnum2 { //indent:0 exp:0
	enum1( //indent:8 exp:8
		v -> v, //indent:16 exp:16
		new Object() //indent:16 exp:16
	), //indent:8 exp:8

	enum2( //indent:8 exp:8
	v //indent:8 exp:8
		-> v, //indent:16 exp:16
		new Object() //indent:16 exp:16
	), //indent:8 exp:8

	enum3( //indent:8 exp:8
v -> v, //indent:0 exp:4 warn
		new Object() //indent:16 exp:16
	), //indent:8 exp:8

	enum4( //indent:8 exp:8
	v -> //indent:8 exp:8
v, //indent:0 exp:0
new Object().toString() //indent:0 exp:4 warn
	), //indent:8 exp:8

	enum5( //indent:8 exp:8
		v //indent:16 exp:16
			-> //indent:24 exp:24
				v, new String //indent:32 exp:32
		("sad🤩") //indent:16 exp:32 warn
	), //indent:8 exp:8

	enum6 //indent:8 exp:8
		( //indent:16 exp:16
			v //indent:24 exp:24
	-> //indent:8 exp:8
v, //indent:0 exp:0
		new Object() //indent:16 exp:16
	), //indent:8 exp:8
	enum7( //indent:8 exp:8
		v //indent:16 exp:16
-> //indent:0 exp:8 warn
			v, //indent:24 exp:24
		new Object() //indent:16 exp:16
	); //indent:8 exp:8
    private final Function<Object, Object> function; //indent:4 exp:4
    private final Object object; //indent:4 exp:4

	InputIndentationLambdaInEnum2(Function<Object, Object> function, Object object) { //indent:4 exp:4
        this.function = function; //indent:8 exp:8
        this.object = object; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0


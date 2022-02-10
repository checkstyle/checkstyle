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

import java.util.function.Predicate; //indent:0 exp:0

import java.util.List; //indent:0 exp:0

public enum InputIndentationLambdaInEnum { //indent:0 exp:0
	/** //indent:8 exp:8
	 * Getter. //indent:9 exp:9
	 * T get() //indent:9 exp:9
	 */ //indent:9 exp:9
	GET(-1, false, 1, m -> m.isEmpty() //indent:8 exp:8
		&& (m.getClass().getSimpleName().startsWith("get") || m.getClass().getSimpleName().startsWith("is"))), //indent:16 exp:16
	/** //indent:8 exp:8
	 * Setter //indent:9 exp:9
	 * void set(T) //indent:9 exp:9
	 */ //indent:9 exp:9
	SET(0, false, 1, m -> m.size() == 1 && m.getClass().getSimpleName().startsWith("set")), //indent:8 exp:8
	// both of the following levels of indentation are acceptable //indent:8 exp:8
	/** //indent:8 exp:8
	 * void add_w(T) //indent:9 exp:9
	 */ //indent:9 exp:9
	// both of the following levels of indentation are acceptable //indent:8 exp:8
	ADD_W(0, true, 10, m -> { //indent:8 exp:8
	if (m.size() == 1) { //indent:8 exp:8
	    if (m.getClass().getSimpleName().startsWith("add") || m.getClass().getSimpleName().startsWith("insert")) { //indent:12 exp:12
	        return m.getClass().getSimpleName().endsWith("AtTop") || m.getClass().getSimpleName().endsWith("Begin"); //indent:16 exp:16
	    } //indent:12 exp:12
	} //indent:8 exp:8
	return false; //indent:8 exp:8
	}), //indent:8 exp:8
	/** //indent:8 exp:8
	 * void add_x(T) //indent:9 exp:9
	 */ //indent:9 exp:9
	ADD_X(0, true, 10, m -> { //indent:8 exp:8
	    if (m.size() == 1) { //indent:12 exp:12
	        if (m.getClass().getSimpleName().startsWith("add") || m.getClass().getSimpleName().startsWith("insert")) { //indent:16 exp:16
	            return m.getClass().getSimpleName().endsWith("AtTop") || m.getClass().getSimpleName().endsWith("Begin"); //indent:20 exp:20
	        } //indent:16 exp:16
	    } //indent:12 exp:12
	    return false; //indent:12 exp:12
	}), //indent:8 exp:8
	/** //indent:8 exp:8
	 * void add_y(T) //indent:9 exp:9
	 */ //indent:9 exp:9
	ADD_Y(0, true, 1, m -> {  //indent:8 exp:8
			if (m.size() == 1) { //indent:24 exp:8,12 warn
				return m.getClass().getSimpleName().startsWith("add") || m.getClass().getSimpleName().startsWith("insert"); //indent:32 exp:12,16 warn
			} //indent:24 exp:8,12 warn
			return false; //indent:24 exp:8,12 warn
	}), //indent:8 exp:8

	/** //indent:8 exp:8
	 * void add_last(T) //indent:9 exp:9
	 */ //indent:9 exp:9
	ADD_LAST(0, true, 1, m ->    {  //indent:8 exp:8
	    if (m.size() == 1) { //indent:12 exp:12
			return m.getClass().getSimpleName().startsWith("add") || m.getClass().getSimpleName().startsWith("insert"); //indent:24 exp:12,16 warn
	    } //indent:12 exp:12
	    return false; //indent:12 exp:12
	}); //indent:8 exp:8

    private final Predicate<List<?>> detector; //indent:4 exp:4
    private final int level; //indent:4 exp:4
    private final boolean multi; //indent:4 exp:4
    private final int valueParameterIndex; //indent:4 exp:4

    InputIndentationLambdaInEnum(int valueParameterIndex, boolean multi, int level, Predicate<List<?>> detector) { //indent:4 exp:4
        this.multi = multi; //indent:8 exp:8
        this.level = level; //indent:8 exp:8
        this.detector = detector; //indent:8 exp:8
        this.valueParameterIndex = valueParameterIndex; //indent:8 exp:8
    } //indent:4 exp:4

} //indent:0 exp:0

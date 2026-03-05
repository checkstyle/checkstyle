package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.ArrayList; //indent:0 exp:0
import java.util.function.Supplier; //indent:0 exp:0


/** //indent:0 exp:0
 * //indent:1 exp:1
 * @author IljaDubinin //indent:1 exp:1
 */ //indent:1 exp:1
public class InputIndentationEmptyArray //indent:0 exp:0
{ //indent:0 exp:0

    public static void test() { //indent:4 exp:4
        method(ArrayList::new); //indent:8 exp:8
    } //indent:4 exp:4

    private static void method(Supplier<?> s) { //indent:4 exp:4
    } //indent:4 exp:4

} //indent:0 exp:0

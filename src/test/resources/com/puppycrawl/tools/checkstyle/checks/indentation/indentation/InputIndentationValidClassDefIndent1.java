package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidClassDefIndent1 //indent:0 exp:0
    extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener { //indent:4 exp:>=4

} //indent:0 exp:0

class InputIndentationValidClassDefIndent2  //indent:0 exp:0
    extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener  //indent:4 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

class InputIndentationValidClassDefIndent3 //indent:0 exp:0
    extends java.awt.event.MouseAdapter  //indent:4 exp:>=4
    implements java.awt.event.MouseListener  //indent:4 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

final class InputIndentationValidClassDefIndent4 //indent:0 exp:0
    extends java.awt.event.MouseAdapter  //indent:4 exp:>=4
    implements java.awt.event.MouseListener  //indent:4 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

final  //indent:0 exp:0
class InputIndentationValidClassDefIndent4a //indent:0 exp:>=4 warn
    extends java.awt.event.MouseAdapter  //indent:4 exp:>=4
    implements java.awt.event.MouseListener  //indent:4 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

final class InputIndentationValidClassDefIndent5 extends Object  //indent:0 exp:0
{ //indent:0 exp:0

} //indent:0 exp:0

class HashingContainer<K, V> { //indent:0 exp:0
    @Deprecated //indent:4 exp:4
    public Object[] table; //indent:4 exp:4

    @Override //indent:4 exp:4
    public String toString() { //indent:4 exp:4
        return ""; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

class Operator { //indent:0 exp:0
    public Operator(String str, OperatorHelper handler) { //indent:4 exp:4
    } //indent:4 exp:4
} //indent:0 exp:0

class OperatorHelper { //indent:0 exp:0
} //indent:0 exp:0

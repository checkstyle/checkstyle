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
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
  public class InputIndentationInvalidClassDefIndent extends Object { //indent:2 exp:0 warn


} //indent:0 exp:0

class InputIndentationInvalidClassDefIndentB extends javax.swing.JButton //indent:0 exp:0
  { //indent:2 exp:0 warn


  } //indent:2 exp:0 warn


  class InputIndentationInvalidClassDefIndentC  //indent:2 exp:0 warn
{ //indent:0 exp:0


  } //indent:2 exp:0 warn



class InputIndentationValidClassDefIndent22  //indent:0 exp:0
  extends java.awt.event.MouseAdapter  //indent:2 exp:>=4 warn
  implements java.awt.event.MouseListener  //indent:2 exp:>=4 warn
{ //indent:0 exp:0

} //indent:0 exp:0

class InputIndentationValidClassDefIndent33 //indent:0 exp:0
  extends java.awt.event.MouseAdapter  //indent:2 exp:>=4 warn
    implements java.awt.event.MouseListener  //indent:4 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

final class InputIndentationValidClassDefIndent44 //indent:0 exp:0
    extends java.awt.event.MouseAdapter  //indent:4 exp:>=4
  implements  //indent:2 exp:>=4 warn
  java.awt.event.MouseListener  //indent:2 exp:>=4 warn
{ //indent:0 exp:0

} //indent:0 exp:0

  final class InputIndentationValidClassDefIndent55 extends Object  //indent:2 exp:0 warn
  { //indent:2 exp:0 warn

} //indent:0 exp:0


final class InputIndentationValidClassDefIndent5b extends Object  //indent:0 exp:0
{ //indent:0 exp:0

  } //indent:2 exp:0 warn


class InputIndentationInvalidClassDefIndentc2 //indent:0 exp:0
  extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener {//indent:2 exp:4 warn


} //indent:0 exp:0

final  //indent:0 exp:0
class InputIndentationValidClassDefIndent4d //indent:0 exp:4 warn
    extends  //indent:4 exp:4
        java.awt.event.MouseAdapter  //indent:8 exp:>=4
    implements  //indent:4 exp:>=4
        java.awt.event.MouseListener  //indent:8 exp:>=4
{ //indent:0 exp:0

} //indent:0 exp:0

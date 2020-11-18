package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import com.puppycrawl.tools.checkstyle.checks.indentation.AbstractExpressionHandler; //indent:0 exp:0

import java.util.List; //indent:0 exp:0
import java.util.Map; //indent:0 exp:0
import java.util.function.Supplier; //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 8                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationAnonymousClassInMethodCurlyOnNewLine //indent:0 exp:0
{ //indent:0 exp:0
    private void aMethod() //indent:4 exp:4
    { //indent:4 exp:4
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup1 = //indent:8 exp:8
                new Supplier<Map<String, List<AbstractExpressionHandler>>>() { //indent:16 exp:>=16
                    @Override //indent:20 exp:20
                    public Map<String, List<AbstractExpressionHandler>> get() //indent:20 exp:20
                    { //indent:20 exp:20
                        return null; //indent:24 exp:24
                    } //indent:20 exp:20
                }; //indent:16 exp:16
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup2 = //indent:8 exp:8
                  new Supplier<Map<String, List<AbstractExpressionHandler>>>(){ //indent:18 exp:>=16
                    @Override //indent:20 exp:20
                    public Map<String, List<AbstractExpressionHandler>> get() //indent:20 exp:20
                    { //indent:20 exp:20
                        return null; //indent:24 exp:24
                    } //indent:20 exp:20
                  }; //indent:18 exp:16,20,24 warn
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup3 = //indent:8 exp:8
              new Supplier<Map<String,List<AbstractExpressionHandler>>>(){ //indent:14 exp:>=16 warn
                    @Override //indent:20 exp:20
                    public Map<String, List<AbstractExpressionHandler>> get() //indent:20 exp:20
                    { //indent:20 exp:20
                        return null; //indent:24 exp:24
                    } //indent:20 exp:20
              }; //indent:14 exp:16,20,24 warn
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup4 = //indent:8 exp:8
                new Supplier<Map<String, List<AbstractExpressionHandler>>>() //indent:16 exp:>=16
                { //indent:16 exp:16
                    @Override //indent:20 exp:20
                    public Map<String, List<AbstractExpressionHandler>> get() //indent:20 exp:20
                    { //indent:20 exp:20
                        return null; //indent:24 exp:24
                    } //indent:20 exp:20
                }; //indent:16 exp:16
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup5 = //indent:8 exp:8
                new Supplier<Map<String, List<AbstractExpressionHandler>>>() //indent:16 exp:>=16
                  { //indent:18 exp:16,20,24 warn
                      @Override //indent:22 exp:22
                      public Map<String, List<AbstractExpressionHandler>> get() //indent:22 exp:22
                      { //indent:22 exp:22
                          return null; //indent:26 exp:26
                      } //indent:22 exp:22
                  }; //indent:18 exp:16,20,24 warn
        final Supplier<Map<String, List<AbstractExpressionHandler>>> sup6 = //indent:8 exp:8
                new Supplier<Map<String, List<AbstractExpressionHandler>>>() //indent:16 exp:>=16
              { //indent:14 exp:16,20,24 warn
                  @Override //indent:18 exp:18
                  public Map<String, List<AbstractExpressionHandler>> get() //indent:18 exp:18
                  { //indent:18 exp:18
                      return null; //indent:22 exp:22
                  } //indent:18 exp:18
              }; //indent:14 exp:16,20,24 warn
    } //indent:4 exp:4
} //indent:0 exp:0


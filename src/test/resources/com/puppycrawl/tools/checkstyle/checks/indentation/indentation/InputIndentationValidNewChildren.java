/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationValidNewChildren { //indent:0 exp:0

    private final StringBuffer mFilter = //indent:4 exp:4
            new StringBuffer( //indent:12 exp:12
                    new CharSequence() { //indent:20 exp:20
                        @Override //indent:24 exp:24
                        public char charAt(int index) { //indent:24 exp:24
                            return 'A'; //indent:28 exp:28
                        } //indent:24 exp:24

                        public int length() { //indent:24 exp:24
                            return 1; //indent:28 exp:28
                        } //indent:24 exp:24

                        public CharSequence subSequence(int start, int end) { //indent:24 exp:24
                            return this; //indent:28 exp:28
                        } //indent:24 exp:24

                        public String toString() { //indent:24 exp:24
                            return "Foo"; //indent:28 exp:28
                        } //indent:24 exp:24
                    }); //indent:20 exp:20
} //indent:0 exp:0

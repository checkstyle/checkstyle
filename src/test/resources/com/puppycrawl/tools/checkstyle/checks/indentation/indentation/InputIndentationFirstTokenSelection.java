package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

import java.util.ArrayList;                                                   //indent:0 exp:0
import java.util.List;                                                        //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationFirstTokenSelection {                            //indent:0 exp:0

    public void beforeAndAfter() {                                            //indent:4 exp:4
        List                                                                  //indent:8 exp:8
<                                                                             //indent:0 exp:8 warn
Integer> x = new ArrayList<Integer                                            //indent:0 exp:12 warn
>();                                                                          //indent:0 exp:12 warn
    }                                                                         //indent:4 exp:4
}                                                                             //indent:0 exp:0

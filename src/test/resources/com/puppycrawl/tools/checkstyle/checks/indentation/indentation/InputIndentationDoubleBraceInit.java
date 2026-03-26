package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.HashMap;                                               //indent:0 exp:0
import java.util.HashSet;                                               //indent:0 exp:0
import java.util.ArrayList;                                             //indent:0 exp:0
import java.util.Map;                                                   //indent:0 exp:0

/**                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following            //indent:1 exp:1
 * configuration:                                                       //indent:1 exp:1
 *                                                                      //indent:1 exp:1
 * arrayInitIndent = 4                                                  //indent:1 exp:1
 * basicOffset = 4                                                      //indent:1 exp:1
 * braceAdjustment = 0                                                  //indent:1 exp:1
 * caseIndent = 4                                                       //indent:1 exp:1
 * forceStrictCondition = false                                         //indent:1 exp:1
 * lineWrappingIndentation = 4                                          //indent:1 exp:1
 * tabWidth = 4                                                         //indent:1 exp:1
 * throwsIndent = 4                                                     //indent:1 exp:1
 */                                                                     //indent:1 exp:1
public class InputIndentationDoubleBraceInit {                          //indent:0 exp:0

    void test() {                                                       //indent:4 exp:4
        var map = new HashMap<>() {{                                    //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:12
            put("KEY2", "VALUE2");                                      //indent:12 exp:12
        }};                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testMultipleEntries() {                                        //indent:4 exp:4
        var map = new HashMap<String, String>() {{                      //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:12
            put("KEY2", "VALUE2");                                      //indent:12 exp:12
            put("KEY3", "VALUE3");                                      //indent:12 exp:12
        }};                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testHashSet() {                                                //indent:4 exp:4
        var set = new HashSet<String>() {{                              //indent:8 exp:8
            add("A");                                                   //indent:12 exp:12
            add("B");                                                   //indent:12 exp:12
        }};                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testArrayList() {                                              //indent:4 exp:4
        var list = new ArrayList<String>() {{                           //indent:8 exp:8
            add("one");                                                 //indent:12 exp:12
            add("two");                                                 //indent:12 exp:12
            add("three");                                               //indent:12 exp:12
        }};                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testNestedInIf() {                                             //indent:4 exp:4
        if (true) {                                                     //indent:8 exp:8
            var map = new HashMap<>() {{                                //indent:12 exp:12
                put("KEY1", "VALUE1");                                  //indent:16 exp:16
            }};                                                         //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    Map<String, String> testReturnDoubleBrace() {                       //indent:4 exp:4
        return new HashMap<>() {{                                       //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:12
        }};                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testSeparateLineInit() {                                       //indent:4 exp:4
        var map = new HashMap<>() {                                     //indent:8 exp:8
            {                                                           //indent:12 exp:12
                put("KEY1", "VALUE1");                                  //indent:16 exp:16
            }                                                           //indent:12 exp:12
        };                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void testMethodArgDoubleBrace() {                                   //indent:4 exp:4
        System.out.println(new HashMap<>() {{                           //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:12
        }});                                                            //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

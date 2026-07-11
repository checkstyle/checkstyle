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

    private final FluentCallChain configLog = new FluentCallChain();    //indent:4 exp:4
    // behavior until #19415                                            //indent:4 exp:4
    void test() {                                                       //indent:4 exp:4
        var map = new HashMap<>() {{                                    //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:16,20 warn
            put("KEY2", "VALUE2");                                      //indent:12 exp:16,20 warn
        }};                                                             //indent:8 exp:12,16 warn
    }                                                                   //indent:4 exp:4

    void testNestedInIf() {                                             //indent:4 exp:4
        if (true) {                                                     //indent:8 exp:8
            var map = new HashMap<>() {{                                //indent:12 exp:12
                put("KEY1", "VALUE1");                                  //indent:16 exp:20,24 warn
            }};                                                         //indent:12 exp:16,20 warn
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    Map<String, String> testReturnDoubleBrace() {                       //indent:4 exp:4
        return new HashMap<>() {{                                       //indent:8 exp:8
            put("KEY1", "VALUE1");                                      //indent:12 exp:16,20 warn
        }};                                                             //indent:8 exp:12,16 warn
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
            put("KEY1", "VALUE1");                                      //indent:12 exp:16,20 warn
        }});                                                            //indent:8 exp:12,16 warn
    }                                                                   //indent:4 exp:4

    void testWrappedMethodArgDoubleBrace() {                            //indent:4 exp:4
        accept("first",                                                 //indent:8 exp:8
            new HashMap<>() {{                                          //indent:12 exp:12
                put("KEY1", "VALUE1");                                  //indent:16 exp:20,24 warn
                put("KEY2", "VALUE2");                                  //indent:16 exp:20,24 warn
            }});                                                        //indent:12 exp:16,20 warn
    }                                                                   //indent:4 exp:4

    void testNestedMethodArgDoubleBrace() {                             //indent:4 exp:4
        accept(expectReadToEnd(new HashMap<>() {{                       //indent:8 exp:8
                    put("KEY1", "VALUE1");                              //indent:20 exp:20
                    put("KEY2", "VALUE2");                              //indent:20 exp:20
                }}));                                                   //indent:16 exp:16
    }                                                                   //indent:4 exp:4

    void testChainedMethodArgDoubleBrace() {                            //indent:4 exp:4
        foo()                                                           //indent:8 exp:8
            .accept(new HashMap<>() {{                                  //indent:12 exp:12
                        put("KEY1", "VALUE1");                          //indent:24 exp:24
                        put("KEY2", "VALUE2");                          //indent:24 exp:24
                    }})                                                 //indent:20 exp:20
            .bar();                                                     //indent:12 exp:12
    }                                                                   //indent:4 exp:4

    void testKafkaStyleDoubleBrace() {                                  //indent:4 exp:4
        foo()                                                           //indent:8 exp:8
                .doAnswer(expectReadToEnd(new HashMap<>() {{            //indent:16 exp:16
                            put("KEY1", "VALUE1");                      //indent:28 exp:28
                            put("KEY2", "VALUE2");                      //indent:28 exp:28
                        }}))                                            //indent:24 exp:24
                .when(configLog).readToEnd();                           //indent:16 exp:16
    }                                                                   //indent:4 exp:4

    private void accept(String name, Map<String, String> values) {}     //indent:4 exp:4
    private void accept(Object value) {}                                //indent:4 exp:4
    private Map<String, String> expectReadToEnd(                        //indent:4 exp:4
            Map<String, String> values) {                               //indent:12 exp:12
        return values;                                                  //indent:8 exp:8
    }                                                                   //indent:4 exp:4
    private FluentCallChain foo() {                                     //indent:4 exp:4
        return new FluentCallChain();                                   //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private static final class FluentCallChain {                        //indent:4 exp:4

        private FluentCallChain accept(Map<String, String> values) {    //indent:8 exp:8
            return this;                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
        private FluentCallChain bar() {                                 //indent:8 exp:8
            return this;                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
        private FluentCallChain doAnswer(Object answer) {               //indent:8 exp:8
            return this;                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
        private FluentCallChain when(FluentCallChain chain) {           //indent:8 exp:8
            return this;                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
        private void readToEnd() {}                                     //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

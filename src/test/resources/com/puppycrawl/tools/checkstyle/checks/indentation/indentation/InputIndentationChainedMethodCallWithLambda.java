/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

public class InputIndentationChainedMethodCallWithLambda {                      //indent:0 exp:0
    interface Stage {                                                           //indent:4 exp:4
        Stage thenCompose(java.util.function.Function<Object, Stage> fn);       //indent:8 exp:8
        void thenAccept(java.util.function.Consumer<Object> fn);                //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method1(String topicName, boolean authoritative) {                     //indent:4 exp:4
        validateAsync(topicName, authoritative)                                 //indent:8 exp:8
                .thenCompose(__ -> getTopicReferenceAsync(topicName))           //indent:16 exp:16
                .thenAccept(topic -> {                                          //indent:16 exp:16
                            // violation below ''if' has incorrect indentation level 28, expected level should be 20.'
                            if (topic == null) {                                //indent:28 exp:20 warn
                                // violation below ''if' child has incorrect indentation level 32, expected level should be 24.'
                                return;                                         //indent:32 exp:24 warn
                            // violation below ''if rcurly' has incorrect indentation level 28, expected level should be 20.'
                            }                                                   //indent:28 exp:20 warn
                        // violation below ''block rcurly' has incorrect indentation level 24, expected level should be 16.'
                        });                                                     //indent:24 exp:16 warn
    }                                                                           //indent:4 exp:4

    void method2(String topicName, boolean authoritative) {                     //indent:4 exp:4
        validateAsync(topicName, authoritative)                                 //indent:8 exp:8
                .thenCompose(__ -> getTopicReferenceAsync(topicName))           //indent:16 exp:16
                .thenAccept(topic -> {                                          //indent:16 exp:16
                    if (topic == null) {                                        //indent:20 exp:20
                        return;                                                 //indent:24 exp:24
                    }                                                           //indent:20 exp:20
                });                                                             //indent:16 exp:16
    }                                                                           //indent:4 exp:4

    private Stage validateAsync(String topicName, boolean authoritative) {      //indent:4 exp:4
        return null;                                                            //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    private Stage getTopicReferenceAsync(String topicName) {                    //indent:4 exp:4
        return null;                                                            //indent:8 exp:8
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0

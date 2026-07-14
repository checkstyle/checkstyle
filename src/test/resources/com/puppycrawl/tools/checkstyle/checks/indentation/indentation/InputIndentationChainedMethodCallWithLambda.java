//                                                                              //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

/* Config:                                                                      //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:     //indent:1 exp:1
 * basicOffset = 4                                                              //indent:1 exp:1
 * braceAdjustment = 0                                                          //indent:1 exp:1
 * caseIndent = 4                                                               //indent:1 exp:1
 * tabWidth = 4                                                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                                  //indent:1 exp:1
 * forceStrictCondition = false                                                 //indent:1 exp:1
 */                                                                             //indent:1 exp:1
public class InputIndentationChainedMethodCallWithLambda {                      //indent:0 exp:0
    interface Stage {                                                           //indent:4 exp:4
        Stage thenCompose(java.util.function.Function<Object, Stage> fn);       //indent:8 exp:8
        void thenAccept(java.util.function.Consumer<Object> fn);                //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method1(String topicName, boolean authoritative) {                     //indent:4 exp:4
        validateAsync(topicName, authoritative)                                 //indent:8 exp:8
                .thenCompose(__ -> getTopicReferenceAsync(topicName))           //indent:16 exp:16
                .thenAccept(topic -> {                                          //indent:16 exp:16
                            if (topic == null) {                                //indent:28 exp:20 warn
                                return;                                         //indent:32 exp:24 warn
                            }                                                   //indent:28 exp:20 warn
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

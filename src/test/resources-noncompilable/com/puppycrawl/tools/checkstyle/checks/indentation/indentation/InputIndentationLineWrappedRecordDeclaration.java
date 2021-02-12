//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0
                                                                                  //indent:82 exp:82
import java.io.IOException;                                                         //indent:0 exp:0
                                                                                  //indent:82 exp:82
/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * braceAdjustment = 0                                                              //indent:1 exp:1
 * caseIndent = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                                      //indent:1 exp:1
 * forceStrictCondition = false                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
                                                                                  //indent:82 exp:82
class InputIndentationLineWrappedRecordDeclaration {                                //indent:0 exp:0
    private interface Interf {                                                      //indent:4 exp:4
        String sayHello();                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    private static record ConcreteRecord(                                           //indent:4 exp:4
        String greeting                                                             //indent:8 exp:8
    ) implements Interf {                                                           //indent:4 exp:4
        @Override                                                                   //indent:8 exp:8
    public String sayHello() {                                                      //indent:4 exp:4
            return greeting();                                                    //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    private static record ConcreteRecord2(                                          //indent:4 exp:4
        String greeting                                                             //indent:8 exp:8
) implements Interf {                                                          //indent:0 exp:4 warn
        @Override                                                                   //indent:8 exp:8
    public String sayHello() {                                                      //indent:4 exp:4
            return greeting();                                                    //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    private static String method(                                                   //indent:4 exp:4
        String greeting                                                             //indent:8 exp:8
    ) throws Exception {                                                            //indent:4 exp:4
        return greeting + "test";                                                   //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    interface SimpleInterface1 {                                                    //indent:4 exp:4
        default                                                                     //indent:8 exp:8
            void                                                                  //indent:12 exp:12
            method()                                                              //indent:12 exp:12
            throws                                                                //indent:12 exp:12
            IOException {                                                         //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
interface SimpleInterface2 {                                                   //indent:0 exp:4 warn
default                                                                        //indent:0 exp:8 warn
void                                                                           //indent:0 exp:4 warn
method()                                                                       //indent:0 exp:4 warn
throws                                                                         //indent:0 exp:4 warn
IOException {                                                                  //indent:0 exp:4 warn
}                                                                              //indent:0 exp:8 warn
}                                                                              //indent:0 exp:4 warn
                                                                                  //indent:82 exp:82
    record SimpleRecord1(                                                           //indent:4 exp:4
    )                                                                               //indent:4 exp:4
            implements                                                            //indent:12 exp:12
            SimpleInterface1                                                      //indent:12 exp:12
    {                                                                               //indent:4 exp:4
        record Inner1(                                                              //indent:8 exp:8
        )                                                                           //indent:8 exp:8
        {                                                                           //indent:8 exp:8
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
record SimpleRecord2(                                                          //indent:0 exp:4 warn
)                                                                              //indent:0 exp:4 warn
implements                                                                     //indent:0 exp:4 warn
SimpleInterface2 {                                                             //indent:0 exp:4 warn
record Inner2                                                                  //indent:0 exp:8 warn
(                                                                              //indent:0 exp:4 warn
)                                                                              //indent:0 exp:8 warn
{                                                                              //indent:0 exp:8 warn
}                                                                              //indent:0 exp:8 warn
}                                                                              //indent:0 exp:4 warn
                                                                                  //indent:82 exp:82
}                                                                                   //indent:0 exp:0

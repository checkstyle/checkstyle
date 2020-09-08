//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0
                                                                                  //indent:82 exp:82
import org.w3c.dom.Node;                                                            //indent:0 exp:0
                                                                                  //indent:82 exp:82
/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * tabwidth = 4                                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
public class InputIndentationRecordsAndCompactCtors {                               //indent:0 exp:0
                                                                                  //indent:82 exp:82
    record MyTestRecord                                                             //indent:4 exp:4
(String string, Record rec) {                                                  //indent:0 exp:8 warn
        private boolean inRecord(Object obj) {                                     //indent:8 exp:8
            int value = 0;                                                        //indent:12 exp:12
            if (obj instanceof Integer i) {                                       //indent:12 exp:12
                value = i;                                                        //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            return value > 10;                                                    //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    record MyTestRecord2() {                                                        //indent:4 exp:4
        MyTestRecord2(String one,                                                   //indent:8 exp:8
String two, String three) {                                                   //indent:0 exp:12 warn
            this();                                                               //indent:12 exp:12
        }                                                                           //indent:8 exp:8
        public MyTestRecord2{}                                                      //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    class MyTestClass2 {                                                            //indent:4 exp:4
        MyTestClass2(){}                                                            //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    record MyTestRecord3(Integer i,                                                 //indent:4 exp:4
                         Node node) {                                             //indent:25 exp:25
        public MyTestRecord3 {                                                      //indent:8 exp:8
int x = 5;                                                                    //indent:0 exp:12 warn
        }                                                                           //indent:8 exp:8
                                                                                  //indent:82 exp:82
        public static void main(String... args) {                                   //indent:8 exp:8
            System.out.println("works!");                                         //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    record MyTestRecord4() {}                                                       //indent:4 exp:4
                                                                                  //indent:82 exp:82
record MyTestRecord5() {                                                       //indent:0 exp:4 warn
        static MyTestRecord mtr =                                                   //indent:8 exp:8
                new MyTestRecord("my string", new MyTestRecord4());               //indent:16 exp:16
        public MyTestRecord5 {                                                      //indent:8 exp:8
                                                                                  //indent:82 exp:82
}                                                                              //indent:0 exp:8 warn
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    class MyTestClass {                                                             //indent:4 exp:4
        private MyTestRecord mtr =                                                  //indent:8 exp:8
                new MyTestRecord("my string", new MyTestRecord4());               //indent:16 exp:16
        public MyTestClass(){                                                       //indent:8 exp:8
                                                                                  //indent:82 exp:82
}                                                                              //indent:0 exp:8 warn
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0

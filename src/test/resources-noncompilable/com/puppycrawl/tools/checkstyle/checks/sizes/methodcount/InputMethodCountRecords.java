//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

/* Config:
 * maxTotal = 2
 * tokens = {CLASS_DEF , ENUM_CONSTANT_DEF , ENUM_DEF , INTERFACE_DEF ,
 *  ANNOTATION_DEF, RECORD_DEF}
 *
 */
public class InputMethodCountRecords {

    record MyTestRecord() { // violation
        void foo() {
        }

        void foo2() {
        }

        void foo3() {
        }

        MyTestRecord(String str){
            this();
        }
    }

    //compact ctor
    record MyTestRecord2() { // should be ok
        void foo() {
        }

        void foo2() {
        }

        public MyTestRecord2 { // make sure no false positive here }
        }
    }
    //compact ctor
    record MyTestRecord3() { // ok
        void foo() {
        }

        Record foo2() {
            record InMethodDef(int x){ // violation
                void foo() {
                }

                void foo2() {
                }

                void foo3() {
                }
            }

            return new InMethodDef(5);
        }

        public MyTestRecord3 { // make sure no false positive here
        }
    }

    record MyTestRecordOuter() { // violation
        void foo() {
        }

        void foo2() {
        }

        void foo3() {
        }

        record MyTestRecordInner1() { // violation
            void foo() {
            }

            void foo2() {
            }

            void foo3() {
            }
            record MyTestRecordInner2() { // violation
                void foo() {
                }

                void foo2() {
                }

                void foo3() {

                }

                Record innerMethod() {
                    record InMethodDef(int x){ // violation
                        void foo() {
                        }

                        void foo2() {
                        }

                        void foo3() {
                        }
                    }

                    return new InMethodDef(5);
                }

            }

        }

    }
}

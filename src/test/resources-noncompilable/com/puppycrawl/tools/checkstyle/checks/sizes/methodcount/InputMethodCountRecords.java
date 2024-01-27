/*
MethodCount
maxTotal = 2
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCountRecords {

    record MyTestRecord() { // violation 'Total number of methods is 3 (max allowed is 2).'
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
    record MyTestRecord3() {
        void foo() {
        }

        Record foo2() {
            record InMethodDef(int x){ // violation 'Total .* methods is 3 (max allowed is 2).'
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

    record MyTestRecordOuter() { // violation 'Total .* methods is 3 (max allowed is 2).'
        void foo() {
        }

        void foo2() {
        }

        void foo3() {
        }

        record MyTestRecordInner1() { // violation 'Total .* methods is 3 (max allowed is 2).'
            void foo() {
            }

            void foo2() {
            }

            void foo3() {
            }
            record MyTestRecordInner2() { // violation 'Total .* methods is 4 (max allowed is 2).'
                void foo() {
                }

                void foo2() {
                }

                void foo3() {

                }

                Record innerMethod() {
                    record InMethodDef(int x){ // violation 'Total .* methods is 3 (.* is 2).'
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

/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisInnerClassAndLimitation {

    class FirstLevel {
        int x;

        FirstLevel(){}
        FirstLevel(int x){
            this();
        }
        class SecondLevel {
            int x;
            void methodInSecondLevel(int y) {
                FirstLevel.this.x = y; // no violation - limitation
                this.x = y; // violation
            }
        }

        void methodInFirstLevel(){
            this.x++; // violation
        }
    }
}

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
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

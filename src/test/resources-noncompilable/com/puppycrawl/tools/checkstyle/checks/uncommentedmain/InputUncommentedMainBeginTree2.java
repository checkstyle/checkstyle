/*
UncommentedMain


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

public record InputUncommentedMainBeginTree2(Integer x) {
    class Test1 {
        class Test2 {
            public static void main(String[] args) {
            // violation 'Uncommented main method found'
            }
        }
    }
}

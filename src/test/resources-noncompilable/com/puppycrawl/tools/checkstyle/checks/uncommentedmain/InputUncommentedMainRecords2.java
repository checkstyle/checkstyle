/*
UncommentedMain
excludedClasses = (default)^$


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

public record InputUncommentedMainRecords2(Integer x) {
    public static void main(String... args) { // violation 'Uncommented main method found'
        System.out.println("no comments here!");
    }

    record Check1(int j){
        record Check2(int k) {
            class Check3{
                class Check4 {
                    record Check5(String K) { // violation below 'Uncommented main method found'
                       public static void main(String[] args) {
                            System.out.println("I am violated");
                        }
                    }
                }
            }
        }
    }
}

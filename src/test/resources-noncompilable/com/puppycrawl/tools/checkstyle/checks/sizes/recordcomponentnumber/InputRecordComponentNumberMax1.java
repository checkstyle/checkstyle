/*
RecordComponentNumber
max = 1
accessModifiers = (default)public, protected, package, private


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Node;

public class InputRecordComponentNumberMax1 {

    public record TestRecord1(int x){
        public TestRecord1{

        }
    }

    public record TestRecord2(int x, int y){ // violation

    }

    public record TestRecord3(String str, int x, int y){ // violation

    }

    public record TestRecord4(Node node, // violation
                              Point x,
                              Point y,
                              int translation,
                              Shape square){
        private static ArrayDeque<String> myDeque = new ArrayDeque<>();
        public TestRecord4 {
            try {
                myDeque.add(x.toString());
                myDeque.add(y.toString());
            } catch (Exception ignored) {

            }
        }
    }

    public record TestRecord5(int x, int y, int z, // violation
                              int a, int b, int c, int d){

    }

    public record TestRecord6(int x, int y, int z, // violation
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k){

    }
    public record TestRecord7(int y){

        record InnerRecordOk(int x, int y, int z){ // violation

        }

        private record InnerRecordBad(int x, int y, int z, // violation
                                      int a, int b, int c,
                                      int d, int e, int f,
                                      int g, int h, int i,
                                      int j, int k){

            private record InnerRecordCeptionBad(int x, int y, int z, // violation
                                                 int a, int b, int c,
                                                 int d, int e, int f,
                                                 int g, int h, int i,
                                                 int j, int k) {

                public record InnerPublicBad(int[] arr, // violation
                                             LinkedHashMap<String, Node> linkedHashMap,
                                             int x,
                                             ArrayDeque<Node> arrayDeque,
                                             List<String> myList,
                                             List<String> myOtherList) {


                }
            }

        }
    }

    public record TestRecord8(int x, int y, int z, String... myVarargs){ // violation

    }

    public record TestRecord9(int x, int y, int z, // violation
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k, String... myVarargs){

    }

    public record TestRecord10(String... myVarargs){}

    public record TestRecord11(int[] arr, // violation
                               LinkedHashMap<String, Node> linkedHashMap,
                               int x){}

    public record TestRecord12(int[] arr, // violation
                               LinkedHashMap<String, Node> linkedHashMap,
                               int x,
                               ArrayDeque<Node> arrayDeque,
                               List<String> myList,
                               List<String> myOtherList){

    }

    private static record MyPrivateRecord1() {}

    private static record MyPrivateRecord2(int x, int y) {} // violation

}

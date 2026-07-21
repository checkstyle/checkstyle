/*
RecordComponentNumber
max = 1
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Node;

public class InputRecordComponentNumberMax1 {
    public record TestRecord1(int x){
        public TestRecord1{}
    }

    // violation below 'Number of record components is 2'
    public record TestRecord2(int x, int y){}

    // violation below 'Number of record components is 3'
    public record TestRecord3(String str, int x, int y){}

    // violation below 'Number of record components is 5'
    public record TestRecord4(Node node,
                              Point x,
                              Point y,
                              int translation,
                              Shape square){
        private static ArrayDeque<String> myDeque = new ArrayDeque<>();
        public TestRecord4 {
            try {
                myDeque.add(x.toString());
                myDeque.add(y.toString());
            } catch (Exception ignored) {}
        }
    }

    // violation below 'Number of record components is 7'
    public record TestRecord5(int x, int y, int z,
                              int a, int b, int c, int d){

    }

    // violation below 'Number of record components is 14'
    public record TestRecord6(int x, int y, int z,
                              int a, int b, int c, int d, int e, int f,
                              int g, int h, int i, int j,
                              int k){

    }
    public record TestRecord7(int y){
        // violation below 'Number of record components is 3'
        record InnerRecordOk(int x, int y, int z){
        }

        // violation below 'Number of record components is 14'
        private record InnerRecordBad(int x, int y, int z,
                                      int a, int b, int c, int d, int e, int f,
                                      int g, int h, int i, int j, int k){

            // violation below 'Number of record components is 14'
            private record InnerRecordCeptionBad(int x, int y, int z,
                                      int a, int b, int c, int d, int e, int f,
                                      int g, int h, int i, int j, int k){

                // violation below 'Number of record components is 6'
                public record InnerPublicBad(int[] arr,
                                             LinkedHashMap<String, Node> linkedHashMap,
                                             int x,
                                             ArrayDeque<Node> arrayDeque,
                                             List<String> myList,
                                             List<String> myOtherList) {

                }
            }

        }
    }

    // violation below 'Number of record components is 4'
    public record TestRecord8(int x, int y, int z, String... myVarargs){}

    // violation below 'Number of record components is 15'
    public record TestRecord9(int x, int y, int z,
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k, String... myVarargs){

    }

    public record TestRecord10(String... myVarargs){}

    // violation below 'Number of record components is 3'
    public record TestRecord11(int[] arr,
                               LinkedHashMap<String, Node> linkedHashMap,
                               int x){}

    // violation below 'Number of record components is 6'
    public record TestRecord12(int[] arr,
                               LinkedHashMap<String, Node> linkedHashMap,
                               int x,
                               ArrayDeque<Node> arrayDeque,
                               List<String> myList,
                               List<String> myOtherList){

    }

    private static record MyPrivateRecord1() {}

    // violation below 'Number of record components is 2'
    private static record MyPrivateRecord2(int x, int y) {}
}

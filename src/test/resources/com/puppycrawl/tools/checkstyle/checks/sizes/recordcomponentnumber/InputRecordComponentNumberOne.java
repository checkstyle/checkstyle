/*
RecordComponentNumber
max = (default)8
accessModifiers = (default)public, protected, package, private


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Node;

public class InputRecordComponentNumberOne {

    public record TestRecord1(int x){
        public TestRecord1{

        }
    }

    public record TestRecord2(int x, int y){

    }

    public record TestRecord3(String str, int x, int y){

    }

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
            } catch (Exception ignored) {

            }
        }
    }

    public record TestRecord5(int x, int y, int z,
                              int a, int b, int c, int d){

    }

    public record TestRecord6(int x, int y, int z, // violation
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k){

    }
    public record TestRecord7(int y){

        record InnerRecordOk(int x, int y, int z){

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
                                             List<String> myOtherList,
                                             int a, int b, int c, int d, int e) {


                }
            }

        }
    }

    public record TestRecord8(int x, int y, int z, String... myVarargs){

    }

    public record TestRecord9(int x, int y, int z, // violation
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k, String... myVarargs){

    }

    class LocalRecordHelper {
        Class<?> m(int x) {
            record R76 (int x) { }
            return R76.class;
        }

        private class R {
            public R(int x) {
            }
        }
    }
}

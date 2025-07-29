/*
RecordComponentNumber
max = (default)8
accessModifiers = private



*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Node;

public class InputRecordComponentNumberPrivateModifierTwo {

  public record TestRecord2(int x){
        public TestRecord2{

        }
  }

  public record TestRecord9(int x, int y, int z,
                              int a, int b, int c,
                              int d, int e, int f,
                              int g, int h, int i,
                              int j, int k, String... myVarargs){

  }

    public record TestRecord10(String... myVarargs){}

    public record TestRecord11(int[] arr, LinkedHashMap<String, Node> linkedHashMap, int x){}

    public record TestRecord12(int[] arr,
                               LinkedHashMap<String, Node> linkedHashMap,
                               int x,
                               ArrayDeque<Node> arrayDeque,
                               List<String> myList,
                               List<String> myOtherList){

    }

    private static record MyPrivateRecord1(int x, int y, int z, // violation
                                           int a, int b, int c,
                                           int d, int e, int f,
                                           int g, int h, int i,
                                           int j, int k, String... myVarargs) {}

    private static record MyPrivateRecord2(int x, int y) {}

    protected static record MyProtectedRecord1(int x, int y) {}

    protected static record MyProtectedRecord2(int x, int y, int z,
                                               int a, int b, int c,
                                               int d, int e, int f,
                                               int g, int h, int i,
                                               int j, int k, String... myVarargs) {}
}

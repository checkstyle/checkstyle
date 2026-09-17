package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.function.IntFunction;

/** Some javadoc. */
public class InputFormattedArrayBracketNoWhitespace {

  private int @Ann [] nums1;

  private int @Ann [] nums2;

  void method() {
    String[] strings = {};

    int[] arr = {1, 2, 3, 4, 5};

    int[][] arr2 = {{arr[0], arr[1], arr[2]}};

    arr2[0][1] = arr[4];

    int[] emptyArray = new int[] {};

    Class<?> cls = int[].class;

    boolean intArray = arr instanceof int[];

    int[][] matrix = new int[2][3];

    foo(arr[1], arr[2]);

    int num = arr[1]++;

    num = arr[1]--;

    num = arr[1] + arr[2] - arr[3] * arr[4] / arr[5];

    num = arr[1] << 1;

    num = arr[1] >> 1;

    IntFunction<int[]> methodRef = int[]::new;
  }

  void foo(int... a) {}

  @java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
  @interface Ann {}
}

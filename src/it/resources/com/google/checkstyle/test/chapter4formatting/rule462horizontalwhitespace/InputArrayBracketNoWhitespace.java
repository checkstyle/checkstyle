package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.function.IntFunction;

/** Some javadoc. */
public class InputArrayBracketNoWhitespace {

  private int @Ann[] nums1;
  // 2 violations above:
  // 'Ann' is not followed by whitespace'
  // '[' is not preceded with whitespace'

  private int @Ann [] nums2;

  void method() {
    String[]strings = {};
    // violation above '']' is not followed by whitespace'

    int [] arr = {1, 2, 3, 4, 5};
    // violation above ''[' is preceded with whitespace'

    int [] [] arr2 = {{arr[0 ], arr[ 1], arr[ 2 ] }};
    // 8 violations above:
    // ''[' is preceded with whitespace'
    // '']' is followed by whitespace'
    // ''[' is preceded with whitespace'
    // '']' is preceded with whitespace'
    // ''[' is followed by whitespace'
    // ''[' is followed by whitespace'
    // '']' is followed by whitespace'
    // '']' is preceded with whitespace'

    arr2[ 0 ][ 1] = arr[4];
    // 3 violations above:
    // ''[' is followed by whitespace'
    // '']' is preceded with whitespace'
    // ''[' is followed by whitespace'

    int[] emptyArray = new int[ ] {};
    // 2 violations above:
    // ''[' is followed by whitespace'
    // '']' is preceded with whitespace'

    Class<?> cls = int [] .class;
    // 3 violations above:
    // ''[' is preceded with whitespace'
    // '']' is followed by whitespace'
    // ''.' is preceded with whitespace'

    boolean intArray = arr instanceof int [];
    // violation above ''[' is preceded with whitespace'

    int [][] matrix = new int[2][3];
    // violation above ''[' is preceded with whitespace'

    foo(arr[1] , arr[2] );
    // 4 violations above:
    // '']' is followed by whitespace'
    // '',' is preceded with whitespace'
    // '']' is followed by whitespace'
    // '')' is preceded with whitespace'

    int num = arr[1] ++;
    // 2 violations above:
    // '']' is followed by whitespace'
    // ''\++' is preceded with whitespace'

    num = arr[1] --;
    // 2 violations above:
    // '']' is followed by whitespace'
    // ''--' is preceded with whitespace'

    num = arr[1]+ arr[2]- arr[3]* arr[4]/ arr[5];
    // 8 violations above:
    // '']' is not followed by whitespace'
    // ''\+' is not preceded with whitespace'
    // '']' is not followed by whitespace'
    // ''\-' is not preceded with whitespace'
    // '']' is not followed by whitespace'
    // ''\*' is not preceded with whitespace'
    // '']' is not followed by whitespace'
    // ''/' is not preceded with whitespace'

    num = arr[1]<< 1;
    // 2 violations above:
    // '']' is not followed by whitespace'
    // '<<' is not preceded with whitespace'

    num = arr[1]>> 1;
    //  2 violations above:
    // '']' is not followed by whitespace'
    // '>>' is not preceded with whitespace'

    IntFunction<int[]> methodRef = int[] ::new;
    // 2 violations above:
    // '']' is followed by whitespace'
    // ''::' is preceded with whitespace'
  }

  void foo(int... a) {}

  @java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
  @interface Ann {}
}

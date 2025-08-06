package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputKrStyleExceptionLocalVariables {

  void test1(int[] nums) {
    if (nums.length > 2)
    { // false-positive, ok until #17569
      // 2 violations above:
      //   ''if lcurly' has incorrect indentation level 4, expected level should be 6'
      //   ''{' at column 5 should be on the previous line'
      int temp = nums[0];
      use(temp);
    } // violation ''if rcurly' has incorrect indentation level 4, expected level should be 6'
    // false-positive above, ok until #17569

    for (int i = 1; i < nums.length; i++)
    { // false-positive, ok until #17569
      // 2 violations above:
      //   ''for lcurly' has incorrect indentation level 4, expected level should be 6'
      //   ''{' at column 5 should be on the previous line'
      int k = i * 2;
      use(k);
    } // violation 'for rcurly' has incorrect indentation level 4, expected level should be 6'
    // false-positive above, ok until 17569
  }

  void test2(int k) {
    {
      int x = k + 2;
      use(x);
    }
  }

  void use(int n) {
    n++;
  }
}

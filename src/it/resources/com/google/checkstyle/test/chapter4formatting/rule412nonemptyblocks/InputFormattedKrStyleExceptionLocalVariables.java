package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedKrStyleExceptionLocalVariables {

  void test1(int[] nums) {
    if (nums.length > 2) {
      int temp = nums[0];
      use(temp);
    }

    for (int i = 1; i < nums.length; i++) {
      int k = i * 2;
      use(k);
    }
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

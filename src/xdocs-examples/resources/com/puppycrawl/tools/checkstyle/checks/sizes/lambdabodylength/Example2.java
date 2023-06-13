/*
LambdaBodyLength
max = 5


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

class Example2 {
  Runnable r = () -> { // violation 'length is 6 .* (max allowed is 5).'
    System.out.println(2); // line 2 of lambda
    System.out.println(3);
    System.out.println(4);
    System.out.println(5);
  };

  Runnable r2 = () -> // violation 'length is 6 .* (max allowed is 5).'
    "someString".concat("1")
                .concat("2")
                .concat("3")
                .concat("4")
                .concat("5")
                .concat("6");

  Runnable r3 = () -> {
    System.out.println(2);
    System.out.println(3);
    System.out.println(4);
  };
}

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.function.Function;
import java.util.function.Supplier;

class InputWhitespaceAroundEmptyTypesAndCycles {

  private void foo() {
    int i = 0;
    String[][] x = {{"foo"}};
    for (int first = 0; first < 5; first++) {}  
    int j = 0;
    while (j == 1) {}  
    do {} while (i == 1);  
  }

  enum EmptyFooEnum {}  

  interface SupplierFunction<T> extends Function<Supplier<T>, T> {}  

  class EmptyFoo {}  
}

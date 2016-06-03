//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.coding;
public interface InputDefaultComesLast2 {

    String toJson(Object one, Object two, Object three);

    String toJson(String document);

    default String toJson(Object one) {
      return toJson(one, one, one);
    }

    default String toJson(Object one, Object two) {
      return toJson(one, one, two);
    }
  }
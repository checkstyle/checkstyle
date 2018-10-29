package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public interface InputDefaultComesLastDefaultMethodsInInterface {

    String toJson(Object one, Object two, Object three);

    String toJson(String document);

    default String toJson(Object one) {
      return toJson(one, one, one);
    }

    default String toJson(Object one, Object two) {
      return toJson(one, one, two);
    }
  }

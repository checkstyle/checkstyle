/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public interface InputDefaultComesLastDefaultMethodsInInterface {

    String toJson(Object one, Object two, Object three);

    String toJson(String document);

    default String toJson(Object one) { // ok
      return toJson(one, one, one);
    }

    default String toJson(Object one, Object two) { // ok
      return toJson(one, one, two);
    }
  }

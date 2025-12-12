// Java22

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.util.HashMap;

/** Tests that unnamed variables (_) are allowed per Google Style. */
public class InputUnnamedVariables {

  record Point(int x, int y) {}

  void testUnnamedVariables() {
    for (var _ : new String[] {"foo"}) {
      System.out.println("iteration");
    }

    var _ = new Thread();

    switch ("foo") {
      case String _ -> System.out.println("bar");
    }

    switch (new Point(0, 0)) {
      case Point(int _, _) -> System.out.println("point");
    }

    try {
      // something
    } catch (Error _) {
      // error handling
    }

    var map = new HashMap<String, Integer>();
    map.computeIfAbsent("foo", _ -> 23);

    var _name = "test"; // violation 'Local variable name '_name' must match pattern'
    var na_me = "test"; // violation 'Local variable name 'na_me' must match pattern'
    var name_ = "test"; // violation 'Local variable name 'name_' must match pattern'
  }
}

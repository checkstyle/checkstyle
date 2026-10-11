package com.google.checkstyle.test.chapter3filestructure.rule3311nomoduleimports;

// non-compiled with javac: Compilable with Java25

import module java.base; // violation ''import module' form of import should be avoided'
import module java.logging; // violation ''import module' form of import should be avoided'

/** Some javadoc. */
public class InputNoModuleImports {
  void method() {
    int a = 1;
  }
}

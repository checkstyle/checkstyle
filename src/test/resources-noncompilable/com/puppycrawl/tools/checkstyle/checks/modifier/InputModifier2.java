//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.modifier;
import java.util.Comparator;
public interface InputModifier2 extends Comparator<Integer> {
    @Override
    default int compare(Integer a, Integer b) {
     return 0;
    }
  }
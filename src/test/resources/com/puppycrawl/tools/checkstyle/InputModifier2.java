package com.puppycrawl.tools.checkstyle;

public interface InputModifier2 extends Comparator<Integer> {
    @Override
    default int compare(Integer a, Integer b) {
     return 0;
    }
  }
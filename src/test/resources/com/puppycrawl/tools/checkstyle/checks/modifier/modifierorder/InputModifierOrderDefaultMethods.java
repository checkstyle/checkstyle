package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;
import java.util.Comparator;

public interface InputModifierOrderDefaultMethods extends Comparator<Integer> {
    @Override
    default int compare(Integer a, Integer b) {
     return 0;
    }
  }

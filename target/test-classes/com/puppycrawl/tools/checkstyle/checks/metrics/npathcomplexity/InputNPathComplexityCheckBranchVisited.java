/*
NPathComplexity
max = 20


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

import java.util.List;

public class InputNPathComplexityCheckBranchVisited{
  static void checkExpiration(List<String> list) {
    // violation above 'NPath Complexity is 37 (max allowed is 20)'
    for (String string : list) {
      if (string.isEmpty()) {
        for (String string2 : list) {
          if (string2 != null) {
          }
          if (string2 != null) {
          }
        }
      } else {
      }

      if (string.isEmpty()) {
        for (String string2 : list) {
          if (string2 != null) {
          }
          if (string2 != null) {
          }
        }
      } else {
      }
    }
  }
}

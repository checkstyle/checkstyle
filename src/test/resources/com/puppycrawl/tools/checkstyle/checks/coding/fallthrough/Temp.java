/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

class Temp {
  public void foo() throws Exception {
      int i = 0;
      while (i >= 0) {
          switch (i) {
              case 7:
                  i++;
                  continue;
              case 11:
                  i++;
                  /* fallthru */
          }
      }
  }}



/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VariableDeclarationUsageDistance"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.Calendar;
// xdoc section -- start
public class Example6 {

  public void calSet(long timeNow, int hh, int min) {
    int minutes = min + 5; // Ok,  block of initialization methods equals to distance = 1
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(timeNow);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.HOUR_OF_DAY, hh);
    cal.set(Calendar.MINUTE, minutes);
  }

  public void calSetAgain(long timeNow, int hh, int min){
    int minutes = min + 5000;  // violation, distance = 6
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(timeNow);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.set(Calendar.HOUR_OF_DAY, hh);
    System.out.println("Hello World");
    cal.set(Calendar.MINUTE, minutes);
  }
}
// xdoc section -- end

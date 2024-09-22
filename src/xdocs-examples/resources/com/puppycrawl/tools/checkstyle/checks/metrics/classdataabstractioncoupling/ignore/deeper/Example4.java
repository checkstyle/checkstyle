/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below, "Class Data Abstraction Coupling is 3 (max allowed is 2)."
public class Example4 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default
  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  BigDecimal bigDecimal = new BigDecimal("0"); // Counted 3
}
// xdoc section -- end

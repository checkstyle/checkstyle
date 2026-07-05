/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.MathContext;

// xdoc section -- start

public class Example1 {
  private Set<Object> set = new HashSet<>();        // Ignored by default
  private Map<Object,Object> map = new HashMap<>(); // Ignored by default
  private Object object = new Object();             // Ignored by default

  private BigInteger bigInteger = new BigInteger("0");  // Counted 1
  private BigDecimal bigDecimal = new BigDecimal("0");  // Counted 2
  private MathContext mathContext = new MathContext(0); // Counted 3
}
// xdoc section -- end

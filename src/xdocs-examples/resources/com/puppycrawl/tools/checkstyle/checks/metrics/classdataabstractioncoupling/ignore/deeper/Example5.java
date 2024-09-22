/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="excludedClasses" value="HashMap, HashSet, Example1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example1;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.Example7;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
public class Example5 {
  Set excluded1 = new HashSet(); // Excluded, not counted
  Map excluded2 = new HashMap(); // Excluded, not counted
  Example1 excluded3 = new Example1(); // Excluded, not counted

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");  // Counted, 2
  // instantiation of 5 distinct counted other user defined classes
  Example2 example2 = new Example2();
  Example3 example3 = new Example3();
  Example4 example4 = new Example4();
  Example6 example6 = new Example6();
  Example7 example7 = new Example7();
}
// xdoc section -- end

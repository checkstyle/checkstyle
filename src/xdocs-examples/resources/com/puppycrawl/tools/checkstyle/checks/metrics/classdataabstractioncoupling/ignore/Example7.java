/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="excludeClassesRegexps" value=".*Reader$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore;

import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example1;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example5;

import java.io.BufferedReader;
import java.io.PipedReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
public class Example7 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  Example1 example1 = new Example1();
  Example2 example2 = new Example2();
  Example3 example3 = new Example3();
  Example4 example4 = new Example4();
  Example5 example5 = new Example5(); // Counted 7

  // Ignored using module excludeClassesRegexps property
  BufferedReader bufferedReader = new BufferedReader(new PipedReader());
}
// xdoc section -- end

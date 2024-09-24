/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="excludedPackages" value="java.io"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore;

// xdoc section -- start
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example4;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.Example6;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.PipedReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// violation below, "Class Data Abstraction Coupling is 8 (max allowed is 7)."
class Example10 {
  Set set = new HashSet(); // HashSet ignored due to default excludedClasses property
  Map map = new HashMap(); // HashMap ignored due to default excludedClasses property

  AtomicInteger atomicInteger = new AtomicInteger();
  BigInteger bigInteger = new BigInteger("0");
  BigDecimal bigDecimal = new BigDecimal("0");
  MathContext mathContext = new MathContext(0);
  Example2 example3 = new Example2();
  Example3 example4 = new Example3();
  Example4 example5 = new Example4();
  Example6 example6 = new Example6();

  // As per the module properties, instances from java.io package are not counted
  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
  PipedReader pipedReader = new PipedReader();
  BufferedReader bufferedReader = new BufferedReader(pipedReader);
  CharArrayWriter charArrayWriter = new CharArrayWriter();
}
// xdoc section -- end

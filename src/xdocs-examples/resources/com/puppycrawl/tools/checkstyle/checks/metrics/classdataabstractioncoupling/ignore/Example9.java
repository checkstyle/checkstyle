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


// xdoc section -- start
import java.io.BufferedReader;

public class Example9 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  BigDecimal bigDecimal = new BigDecimal("0");
  MathContext mathContext = new MathContext(0); // Counted 4

  // Ignored using module excludedPackages property
  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
  CharArrayWriter charArrayWriter = new CharArrayWriter();
  PipedReader pipedReader = new PipedReader();
  BufferedReader bufferedReader = new BufferedReader(pipedReader);
}
// xdoc section -- end

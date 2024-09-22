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

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below, "Class Data Abstraction Coupling is 8 (max allowed is 7)."
public class Example8 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  BigDecimal bigDecimal = new BigDecimal("0");
  MathContext mathContext = new MathContext(0);
  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
  CharArrayWriter charArrayWriter = new CharArrayWriter();
  StringWriter stringWriter = new StringWriter();
  File file = new File("path"); // Counted 8
}
// xdoc section -- end

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
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.UseCase1;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.Example3;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase2;
import com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.ignore.deeper.UseCase3;

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

// violation below "Class Data Abstraction Coupling is 8 (max allowed is 7)."
class UseCase5 {
  Set set = new HashSet(); // Ignored by default
  Map map = new HashMap(); // Ignored by default

  AtomicInteger atomicInteger = new AtomicInteger(); // Counted 1
  BigInteger bigInteger = new BigInteger("0");
  BigDecimal bigDecimal = new BigDecimal("0");
  MathContext mathContext = new MathContext(0);
  UseCase1 example3 = new UseCase1();
  Example3 example4 = new Example3();
  UseCase2 example5 = new UseCase2();
  UseCase3 useCase3 = new UseCase3(); // Counted 8

  // Ignored using module excludedPackages property
  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
  PipedReader pipedReader = new PipedReader();
  BufferedReader bufferedReader = new BufferedReader(pipedReader);
  CharArrayWriter charArrayWriter = new CharArrayWriter();
}
// xdoc section -- end

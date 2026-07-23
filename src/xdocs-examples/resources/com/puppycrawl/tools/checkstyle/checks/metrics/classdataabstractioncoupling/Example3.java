/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ClassDataAbstractionCoupling">
      <property name="max" value="9"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.PipedReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

// xdoc section -- start
// violation below "Class Data Abstraction Coupling is 10 (max allowed is 9)."
public class Example3 {
  private Set<Object> set = new HashSet<>();         // ok, ignored
  private Map<Object, Object> map = new HashMap<>(); // ok, ignored
  private Object object = new Object();              // ok, ignored

  private AtomicInteger atomicInteger = new AtomicInteger();
  private BigInteger bigInteger = new BigInteger("0");
  private BigDecimal bigDecimal = new BigDecimal("0");
  private MathContext mathContext = new MathContext(0);

  private Example1 example1 = new Example1(); // ok, ignored
  private UseCase1 useCase1 = new UseCase1();

  private ByteArrayInputStream byteArrayInputStream =
          new ByteArrayInputStream(new byte[1]);
  private CharArrayWriter charArrayWriter = new CharArrayWriter();

  private PipedReader pipedReader = new PipedReader();
  private BufferedReader bufferedReader =
          new BufferedReader(pipedReader);
}
// xdoc section -- end

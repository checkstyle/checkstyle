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
import java.time.Instant;
import java.time.LocalTime;

// xdoc section -- start
public class Example1 {
  private Set<Object> set = new HashSet<>(); // Ignored by default
  private Map<Object,Object> map = new HashMap<>(); // Ignored by default
  private Object object = new Object(); // Ignored by default

  private Instant instant = Instant.now(); // Counted 1
  private LocalTime localTime = LocalTime.now(); // Counted 2
}
// xdoc section -- end

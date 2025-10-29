/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalType">
      <property name="validateAbstractClassNames" value="true"/>
      <property name="legalAbstractClassNames" value="AbstractList"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;

// xdoc section -- start
public class Example9 {

    // violation below 'Usage of type 'AbstractSet' is not allowed'
    public AbstractSet<String> getItems() {
        return new HashSet<>();
    }

    public AbstractList<String> getNames() {
        return new ArrayList<>();
    }
}
// xdoc section -- end

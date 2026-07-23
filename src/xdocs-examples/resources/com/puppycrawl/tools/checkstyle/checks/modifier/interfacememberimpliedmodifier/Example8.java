/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceMemberImpliedModifier">
      <property name="violateImpliedStaticField" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

import java.util.List;

// xdoc section -- start
public interface Example8 {

  public static final String UNKNOWN = "Unknown";
  String OTHER = "Other";
  // 2 violations above:
  //    'Implied modifier 'final' should be explicit'
  //    'Implied modifier 'public' should be explicit'
  // ok, because 'violateImpliedStaticField' is false
  public static Address instance() {return null;}

  public abstract Address createAddress(String addressLine, String city);
  List<Address> findAddresses(String city);
  // 2 violations above:
  //    'Implied modifier 'abstract' should be explicit'
  //    'Implied modifier 'public' should be explicit'
  interface Address {
    // 2 violations above:
    //    'Implied modifier 'public' should be explicit'
    //    'Implied modifier 'static' should be explicit'
    String getCity();
    // 2 violations above:
    //    'Implied modifier 'abstract' should be explicit'
    //    'Implied modifier 'public' should be explicit'
  }
}
// xdoc section -- end

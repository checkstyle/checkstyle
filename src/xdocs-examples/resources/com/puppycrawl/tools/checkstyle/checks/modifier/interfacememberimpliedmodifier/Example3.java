/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceMemberImpliedModifier">
      <property name="violateImpliedPublicField" value="false"/>
      <property name="violateImpliedStaticField" value="false"/>
      <property name="violateImpliedFinalField" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

import java.util.List;

// xdoc section -- start
public interface Example3 {

  public static final String UNKNOWN = "Unknown";
  String OTHER = "Other";

  public static Example3 instance() { return null; }

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
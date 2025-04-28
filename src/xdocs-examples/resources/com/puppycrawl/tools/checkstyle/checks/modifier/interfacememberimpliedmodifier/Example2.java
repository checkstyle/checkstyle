/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceMemberImpliedModifier">
      <property name="violateImpliedPublicNested" value="false"/>
      <property name="violateImpliedStaticNested" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

import java.util.List;

// xdoc section -- start
public interface Example2 {

  public static final String UNKNOWN = "Unknown";
  String OTHER = "Other";
  // 3 violations above:
  //    'Implied modifier 'final' should be explicit'
  //    'Implied modifier 'public' should be explicit'
  //    'Implied modifier 'static' should be explicit'
  public static Example2 instance() { return null; }

  public abstract Address createAddress(String addressLine, String city);
  List<Address> findAddresses(String city);
  // 2 violations above:
  //    'Implied modifier 'abstract' should be explicit'
  //    'Implied modifier 'public' should be explicit'
  interface Address {
    // ok above because of configured properties
    // ok above because of configured properties

    String getCity();
    // 2 violations above:
    //    'Implied modifier 'abstract' should be explicit'
    //    'Implied modifier 'public' should be explicit'
  }
}
// xdoc section -- end

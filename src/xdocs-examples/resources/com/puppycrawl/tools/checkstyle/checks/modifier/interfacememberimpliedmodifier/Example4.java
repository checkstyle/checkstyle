/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceMemberImpliedModifier">
      <property name="violateImpliedPublicMethod" value="false"/>
      <property name="violateImpliedAbstractMethod" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

import java.util.List;

// xdoc section -- start
public interface Example4 {

  public static final String UNKNOWN = "Unknown";
  String OTHER = "Other";
  // 3 violations above:
  //    'Implied modifier 'final' should be explicit'
  //    'Implied modifier 'public' should be explicit'
  //    'Implied modifier 'static' should be explicit'
  public static Address instance() {return null;}

  public abstract Address createAddress(String addressLine, String city);
  List<Address> findAddresses(String city);
  // ok, because 'violateImpliedAbstractMethod' is false
  // ok, because 'violateImpliedPublicMethod' is false

  interface Address {
    // 2 violations above:
    //    'Implied modifier 'public' should be explicit'
    //    'Implied modifier 'static' should be explicit'
    String getCity();
    // ok, because 'violateImpliedAbstractMethod' is false
    // ok, because 'violateImpliedPublicMethod' is false

  }
}
// xdoc section -- end

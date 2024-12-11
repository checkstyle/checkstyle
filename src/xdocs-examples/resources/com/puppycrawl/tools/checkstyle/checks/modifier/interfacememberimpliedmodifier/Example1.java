/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InterfaceMemberImpliedModifier"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

import java.util.List;

// xdoc section -- start
class Example1 {
  public interface AddressFactory {

    public static final String UNKNOWN = "Unknown";
    // violation below, 'Implied modifier 'final' should be explicit'
    String OTHER = "Other";
    // violation above, 'Implied modifier 'public' should be explicit'
    // violation 2 lines above 'Implied modifier 'static' should be explicit'
    public static AddressFactory instance() {
      return null;
    }

    public abstract Address createAddress(String addressLine, String city);
    List<Address> findAddresses(String city);
    // violation above, 'Implied modifier 'abstract' should be explicit'
    // violation 2 lines above 'Implied modifier 'public' should be explicit'
    interface Address {
      // violation above, 'Implied modifier 'public' should be explicit'
      // violation 2 lines above 'Implied modifier 'static' should be explicit'
      String getCity();
      // violation above, 'Implied modifier 'abstract' should be explicit'
      // violation 2 lines above 'Implied modifier 'public' should be explicit'
    }
  }
}
// xdoc section -- end

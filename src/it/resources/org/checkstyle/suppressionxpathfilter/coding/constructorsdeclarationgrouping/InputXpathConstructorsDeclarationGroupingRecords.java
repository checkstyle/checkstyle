/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = (default)false


*/

// Java14

package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingRecords {
  public record MyRecord(int x, int y) {

    public MyRecord(int a) {
      this(a,a);
    }

    void foo() {}

    // violation below 'Constructors should be grouped together. The last grouped constructor is declared at line '15'.'
    public MyRecord {} // warn
  }
}

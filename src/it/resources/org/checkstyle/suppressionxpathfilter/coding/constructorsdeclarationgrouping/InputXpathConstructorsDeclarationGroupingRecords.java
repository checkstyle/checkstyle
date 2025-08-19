// Java14

package org.checkstyle.suppressionxpathfilter.coding.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingRecords {
  public record MyRecord(int x, int y) {

    public MyRecord(int a) {
      this(a,a);
    }

    void foo() {}

    public MyRecord {} // warn
  }
}

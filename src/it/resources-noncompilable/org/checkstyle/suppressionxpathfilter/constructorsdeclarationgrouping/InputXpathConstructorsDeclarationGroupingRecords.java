//non-compiled with javac: Compilable with Java14

package org.checkstyle.suppressionxpathfilter.constructorsdeclarationgrouping;

public class InputXpathConstructorsDeclarationGroupingRecords {
  public record MyRecord(int x, int y) {

    public MyRecord(int a) {
      this(a,a);
    }

    void foo() {}

    public MyRecord {} // warn

    public MyRecord(int x, int y, int z) {
      this(x+y, z);
    }
  }
}

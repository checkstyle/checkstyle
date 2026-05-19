/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.constructorsdeclarationgrouping;

public class InputConstructorsDeclarationGroupingArity {
    public InputConstructorsDeclarationGroupingArity(int i, int j) {
    }

    public InputConstructorsDeclarationGroupingArity() {
    } // violation above 'Constructors should be ordered by increasing parameter count.'

    public InputConstructorsDeclarationGroupingArity(Object o, int i) {
    } // violation above 'Constructors should be ordered by increasing parameter count.'

    public InputConstructorsDeclarationGroupingArity(Object o) {
    } // violation above 'Constructors should be ordered by increasing parameter count.'

    private enum ExampleEnum {

    ONE, TWO, THREE;

    ExampleEnum() {}

    ExampleEnum(int x, int y) {}

    ExampleEnum(String s) {}
    // violation above 'Constructors should be ordered by increasing parameter count.'
  }
}

/*
CovariantEquals


*/

package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

/**
 * Test file for covariant equals methods.
 * @author Rick Giles
 */
public class InputCovariantEqualsTwo {
  class InputGenericCovariant7 {
    public <A> boolean equals(InputGenericCovariant7 aInputCovariant7)
    {
        return true;
    }

    public boolean equals(Object aObject)
    {
        return false;
    }
  }

  class InputGenericCovariant8 {
    public <A> boolean equals(InputGenericCovariant8 aInputCovariant8)
    {
        return true;
    }

    public boolean equals(Object aObject)
    {
        return false;
    }
  }

  enum InputEnumCovariant {
    EQUALS;

    public boolean equals(InputEnumCovariant obj) { // violation 'covariant equals'
        return false;
    }

    int equals(Integer integer) { // violation 'covariant equals'
        return 0;
    }
  }
}

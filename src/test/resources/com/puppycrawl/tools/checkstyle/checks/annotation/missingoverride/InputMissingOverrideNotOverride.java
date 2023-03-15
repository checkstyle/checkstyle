/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideNotOverride
{
  /**
   * {@inheritDoc}
   */
  private void bleh() {} // violation 'Javadoc {@inheritDoc} tag is not valid at this location.'

  /**
   * {@inheritDoc}
   */
  public static void eh() {} // violation 'Javadoc {@inheritDoc} tag is not valid at this location.'

  /**
   * {@inheritDoc}
   */
  public String junk = "";        // ok

  void dodoo() {}
}

package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

/** Some javadoc. */
public class InputNonConstantNamesBasic {
  public int mPublic; // violation 'Member name 'mPublic' must match pattern'
  protected int mProtected; // violation 'Member name 'mProtected' must match pattern'
  int mPackage; // violation 'Member name 'mPackage' must match pattern'
  private int mPrivate; // violation 'Member name 'mPrivate' must match pattern'

  public int _public; // violation 'Member name '_public' must match pattern'
  protected int prot_ected; // violation 'Member name 'prot_ected' must match pattern'
  int package_; // violation 'Member name 'package_' must match pattern'
  private int priva$te; // violation 'Member name 'priva\$te' must match pattern'

  public int ppublic;
  protected int pprotected;
  int ppackage;
  private int pprivate;

  int ABC = 0;
  // 2 violations above:
  //  'Abbreviation in name 'ABC' must contain no more than '1' consecutive capital letters.'
  //  'Member name 'ABC' must match pattern'
  final int C_D_E = 0; // violation 'Member name 'C_D_E' must match pattern'

  public int $mPublic; // violation 'Member name '\$mPublic' must match pattern'
  protected int mPro$tected; // violation 'Member name 'mPro\$tected' must match pattern'
  int mPackage$; // violation 'Member name 'mPackage\$' must match pattern'
}

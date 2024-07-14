package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

public class InputNonConstantNamesBasic {
  public int mPublic; // violation 'Member name 'mPublic' must match pattern'
  protected int mProtected; // violation 'Member name 'mProtected' must match pattern'
  int mPackage; // violation 'Member name 'mPackage' must match pattern'
  private int mPrivate; // violation 'Member name 'mPrivate' must match pattern'

  public int _public; // violation 'Member name '_public' must match pattern'
  protected int prot_ected; // violation 'Member name 'prot_ected' must match pattern'
  int package_; // violation 'Member name 'package_' must match pattern'
  private int priva$te; // violation 'Member name .* must match pattern'

  public int ppublic; // ok
  protected int pprotected; // ok
  int ppackage; // ok
  private int pprivate; // ok

  int ABC = 0; // violation 'Member name 'ABC' must match pattern'
  final int C_D_E = 0; // violation 'Member name 'C_D_E' must match pattern'

  public int $mPublic; // violation 'Member name .* must match pattern'
  protected int mPro$tected; // violation 'Member name .* must match pattern'
  int mPackage$; // violation 'Member name .* must match pattern'
}

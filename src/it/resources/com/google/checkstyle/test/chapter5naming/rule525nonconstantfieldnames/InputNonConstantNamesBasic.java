package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

/** Some javadoc. */
public class InputNonConstantNamesBasic {
  // violation below, ''mPublic' must .* avoid single lowercase letter followed by uppercase'
  public int mPublic;
  // violation below, ''mProtected' must .* avoid single lowercase letter followed by uppercase'
  protected int mProtected;
  // violation below, ''mPackage' must .* avoid single lowercase letter followed by uppercase'
  int mPackage;
  // violation below, ''mPrivate' must .* avoid single lowercase letter followed by uppercase'
  private int mPrivate;

  public int _public; // violation ''_public' .* underscores allowed only between adjacent digits.'
  // violation below, ''prot_ected' .* underscores allowed only between adjacent digits.'
  protected int prot_ected;
  int package_; // violation ''package_' .* underscores allowed only between adjacent digits.'
  // violation below, ''priva\$te' must .* contain only letters, digits or underscores'
  private int priva$te;

  public int ppublic;
  protected int pprotected;
  int ppackage;
  private int pprivate;

  // 2 violations 3 lines below:
  //  'Abbreviation in name 'ABC' must contain no more than '1' consecutive capital letters.'
  //  'Non-constant field name 'ABC' must start lowercase, be at least 2 chars'
  int ABC = 0;
  final int C_D_E = 0; // violation ''C_D_E' .* underscores allowed only between adjacent digits.'

  // violation below, ''\$mPublic' must .* contain only letters, digits or underscores'
  public int $mPublic;
  // violation below, ''mPro\$tected' must .* contain only letters, digits or underscores'
  protected int mPro$tected;
  // violation below, ''mPackage\$' must .* contain only letters, digits or underscores'
  int mPackage$;
}

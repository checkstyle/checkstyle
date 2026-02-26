package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

/** Some javadoc. */
public class InputNonConstantNamesBasic {
  public int mPublic;
  // violation above, ''mPublic' must .* avoid single lowercase letter followed by uppercase'
  protected int mProtected;
  // violation above, ''mProtected' must .* avoid single lowercase letter followed by uppercase'
  int mPackage;
  // violation above, ''mPackage' must .* avoid single lowercase letter followed by uppercase'
  private int mPrivate;
  // violation above, ''mPrivate' must .* avoid single lowercase letter followed by uppercase'

  public int _public; // violation ''_public' .* underscores allowed only between adjacent digits.'
  protected int prot_ected;
  // violation above, ''prot_ected' .* underscores allowed only between adjacent digits.'
  int package_; // violation ''package_' .* underscores allowed only between adjacent digits.'
  private int priva$te;
  // violation above, ''priva\$te' must .* contain only letters, digits or underscores'

  public int ppublic;
  protected int pprotected;
  int ppackage;
  private int pprivate;

  int ABC = 0;
  // 2 violations above:
  //  'Abbreviation in name 'ABC' must contain no more than '1' consecutive capital letters.'
  //  'Non-constant field name 'ABC' must start lowercase, be at least 2 chars'
  final int C_D_E = 0; // violation ''C_D_E' .* underscores allowed only between adjacent digits.'

  public int $mPublic;
  // violation above, ''\$mPublic' must .* contain only letters, digits or underscores'
  protected int mPro$tected;
  // violation above, ''mPro\$tected' must .* contain only letters, digits or underscores'
  int mPackage$;
  // violation above, ''mPackage\$' must .* contain only letters, digits or underscores'
}

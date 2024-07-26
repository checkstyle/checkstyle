package com.google.checkstyle.test.chapter5naming.rule522classnames;

class inputHeaderClass {
  // 2 violations above:
  //  'The name of the outer type and the file do not match.'
  //  'Type name 'inputHeaderClass' must match pattern'

  // violation below 'Type name 'InputHeader___Interface' .* .*'
  public interface InputHeader___Interface {}

  // violation below 'Type name 'inputHeaderEnum' must match pattern'
  public enum inputHeaderEnum {
    one,
    two
  }

  class NoValid$Name {}
  // violation above 'Type name 'NoValid\$Name' must match pattern'

  class $NoValidName {}
  // violation above 'Type name '\$NoValidName' must match pattern'

  class NoValidName$ {}
  // violation above 'Type name 'NoValidName\$' must match pattern'


  class ValidName {}

  class _ValidName {} // violation 'Type name '_ValidName' must match pattern'

  class Valid_Name {} // violation 'Type name 'Valid_Name' must match pattern'

  class ValidName_ {} // violation 'Type name 'ValidName_' must match pattern'

  interface Foo {}

  interface _Foo {} // violation 'Type name '_Foo' must match pattern'

  interface Fo_o {} // violation 'Type name 'Fo_o' must match pattern'

  interface Foo_ {} // violation 'Type name 'Foo_' must match pattern'

  interface $Foo {} // violation 'Type name '\$Foo' must match pattern'

  interface Fo$o {} // violation 'Type name 'Fo\$o' must match pattern'

  interface Foo$ {} // violation 'Type name 'Foo\$' must match pattern'

  enum FooEnum {}

  enum _FooEnum {} // violation 'Type name '_FooEnum' must match pattern'

  enum Foo_Enum {} // violation 'Type name 'Foo_Enum' must match pattern'

  enum FooEnum_ {} // violation 'Type name 'FooEnum_' must match pattern'

  enum $FooEnum {} // violation 'Type name '\$FooEnum' must match pattern'

  enum Foo$Enum {} // violation 'Type name 'Foo\$Enum' must match pattern'

  enum FooEnum$ {} // violation 'Type name 'FooEnum\$' must match pattern'

  class aaa {} // violation 'Type name 'aaa' must match pattern'

  interface bbb {} // violation 'Type name 'bbb' must match pattern'

  enum ccc {} // violation 'Type name 'ccc' must match pattern'

  @interface Annotation {}

  @interface _Annotation {}
  // violation above 'Type name '_Annotation' must match pattern'

  @interface Annot_ation {}
  // violation above 'Type name 'Annot_ation' must match pattern'

  @interface Annotation_ {}
  // violation above 'Type name 'Annotation_' must match pattern'

  @interface $Annotation {}
  // violation above 'Type name '\$Annotation' must match pattern'

  @interface Annot$ation {}
  // violation above 'Type name 'Annot\$ation' must match pattern'

  @interface Annotation$ {}
  // violation above 'Type name 'Annotation\$' must match pattern'
}

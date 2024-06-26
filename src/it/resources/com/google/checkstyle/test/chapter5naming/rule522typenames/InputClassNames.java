package com.google.checkstyle.test.chapter5naming.rule522typenames;

class inputHeaderClass {
    // violation above 'Type name 'inputHeaderClass' must match pattern'

    public interface InputHeader___Interface {};
    // violation above 'Type name 'InputHeader___Interface' .* .*'

    public enum inputHeaderEnum { one, two };
    // violation above 'Type name 'inputHeaderEnum' must match pattern'

    class NoValid$Name {}
    // violation above 'Type name '.*' must match pattern'

    class $NoValidName {}
    // violation above 'Type name '.*' must match pattern'

    class NoValidName$ {}
    // violation above 'Type name '.*' must match pattern'

}

class ValidName {} //ok

class _ValidName {} // violation 'Type name '_ValidName' must match pattern'

class Valid_Name {} // violation 'Type name 'Valid_Name' must match pattern'

class ValidName_ {} // violation 'Type name 'ValidName_' must match pattern'

interface Foo {} // ok

interface _Foo {} // violation 'Type name '_Foo' must match pattern'

interface Fo_o {} // violation 'Type name 'Fo_o' must match pattern'

interface Foo_ {} // violation 'Type name 'Foo_' must match pattern'

interface $Foo {} // violation 'Type name '.*' must match pattern'

interface Fo$o {} // violation 'Type name '.*' must match pattern'

interface Foo$ {} // violation 'Type name '.*' must match pattern'

enum FooEnum {} //ok

enum _FooEnum {} // violation 'Type name '_FooEnum' must match pattern'

enum Foo_Enum {} // violation 'Type name 'Foo_Enum' must match pattern'

enum FooEnum_ {} // violation 'Type name 'FooEnum_' must match pattern'

enum $FooEnum {} // violation 'Type name '.*' must match pattern'

enum Foo$Enum {} // violation 'Type name '.*' must match pattern'

enum FooEnum$ {} // violation 'Type name '.*' must match pattern'

class aaa {} // violation 'Type name 'aaa' must match pattern'

interface bbb {} // violation 'Type name 'bbb' must match pattern'

enum ccc {} // violation 'Type name 'ccc' must match pattern'

@interface Annotation {} //ok

@interface _Annotation {}
// violation above 'Type name '_Annotation' must match pattern'

@interface Annot_ation {}
// violation above 'Type name 'Annot_ation' must match pattern'

@interface Annotation_ {}
// violation above 'Type name 'Annotation_' must match pattern'

@interface $Annotation {}
// violation above 'Type name '.*' must match pattern'

@interface Annot$ation {}
// violation above 'Type name '.*' must match pattern'

@interface Annotation$ {}
// violation above 'Type name '.*' must match pattern'

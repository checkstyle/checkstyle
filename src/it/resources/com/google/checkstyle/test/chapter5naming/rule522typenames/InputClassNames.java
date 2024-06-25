package com.google.checkstyle.test.chapter5naming.rule522typenames;

class inputHeaderClass {
    // violation above 'Type name 'inputHeaderClass' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

    public interface InputHeader___Interface {};
    // violation above 'Type name 'InputHeader___Interface' .* '.*[A-Z][a-zA-Z0-9].*'.'

    public enum inputHeaderEnum { one, two };
    // violation above 'Type name 'inputHeaderEnum' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

    class NoValid$Name {}
    // violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

    class $NoValidName {}
    // violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

    class NoValidName$ {}
    // violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

}

class ValidName {} //ok

class _ValidName {} // violation 'Type name '_ValidName' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

class Valid_Name {} // violation 'Type name 'Valid_Name' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

class ValidName_ {} // violation 'Type name 'ValidName_' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface Foo {} // ok

interface _Foo {} // violation 'Type name '_Foo' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface Fo_o {} // violation 'Type name 'Fo_o' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface Foo_ {} // violation 'Type name 'Foo_' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface $Foo {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface Fo$o {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface Foo$ {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum FooEnum {} //ok

enum _FooEnum {} // violation 'Type name '_FooEnum' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum Foo_Enum {} // violation 'Type name 'Foo_Enum' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum FooEnum_ {} // violation 'Type name 'FooEnum_' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum $FooEnum {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum Foo$Enum {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum FooEnum$ {} // violation 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

class aaa {} // violation 'Type name 'aaa' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

interface bbb {} // violation 'Type name 'bbb' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

enum ccc {} // violation 'Type name 'ccc' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface Annotation {} //ok

@interface _Annotation {}
// violation above 'Type name '_Annotation' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface Annot_ation {}
// violation above 'Type name 'Annot_ation' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface Annotation_ {}
// violation above 'Type name 'Annotation_' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface $Annotation {}
// violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface Annot$ation {}
// violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

@interface Annotation$ {}
// violation above 'Type name '.*' must match pattern '.*[A-Z][a-zA-Z0-9].*'.'

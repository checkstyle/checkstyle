package com.google.checkstyle.test.chapter5naming.rule522typenames;

class inputHeaderClass { //warn

    public interface InputHeader___Interface {}; //warn

    public enum inputHeaderEnum { one, two }; //warn

    class NoValid$Name {} //warn

    class $NoValidName {} //warn

    class NoValidName$ {} //warn

}

class ValidName {} //ok

class _ValidName {} //warn

class Valid_Name {} //warn

class ValidName_ {} //warn

interface Foo {} // ok

interface _Foo {} // warn

interface Fo_o {} // warn

interface Foo_ {} // warn

interface $Foo {} // warn

interface Fo$o {} // warn

interface Foo$ {} // warn

enum FooEnum {} //ok

enum _FooEnum {} //warn

enum Foo_Enum {} //warn

enum FooEnum_ {} //warn

enum $FooEnum {} //warn

enum Foo$Enum {} //warn

enum FooEnum$ {} //warn

class aaa {} //warn

interface bbb {} //warn

enum ccc {} //warn

@interface Annotation {} //ok

@interface _Annotation {} //warn

@interface Annot_ation {} //warn

@interface Annotation_ {} //warn

@interface $Annotation {} //warn

@interface Annot$ation {} //warn

@interface Annotation$ {} //warn

/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.typename;

public class InputTypeNameRecords {
    interface FirstName {
    }

    class SecondName {
    }

    enum Third_Name {} // violation 'Name 'Third_Name' must match pattern'

    class FourthName_ { // violation 'Name 'FourthName_' must match pattern'
    }

    record My_Record() { // violation 'Name 'My_Record' must match pattern'
        record Inner__Record(Record MyRecord) { // violation 'Name 'Inner__Record' must match pattern'
            public Inner__Record{}
        }
    }

    record MyRecord__() { // violation 'Name 'MyRecord__' must match pattern'
    }

    record GoodName() {
    }
}

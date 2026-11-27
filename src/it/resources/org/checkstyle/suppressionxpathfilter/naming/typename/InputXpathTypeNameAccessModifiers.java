/*
TypeName
format = ^[a-z](_?[a-zA-Z0-9]+)*$
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/
package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameAccessModifiers {

  public interface FirstName {}
  public class SecondName {
    protected class ThirdName {}  // violation 'must match pattern'
  }
}

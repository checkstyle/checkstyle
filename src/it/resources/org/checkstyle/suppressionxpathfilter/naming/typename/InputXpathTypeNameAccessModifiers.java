/*
TypeName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = false
format = ^[a-z](_?[a-zA-Z0-9]+)*$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameAccessModifiers {

  public interface FirstName {}
  public class SecondName {
    // violation below "Name 'ThirdName' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'."
    protected class ThirdName {}
  }
}

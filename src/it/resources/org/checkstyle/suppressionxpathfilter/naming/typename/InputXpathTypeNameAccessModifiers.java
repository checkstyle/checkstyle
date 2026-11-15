/*
TypeName
format = ^[a-z](_?[a-zA-Z0-9]+)*$
applyToPublic = false
tokens = (default)CLASS_DEF,INTERFACE_DEF,ENUM_DEF,ANNOTATION_DEF,RECORD_DEF
applyToProtected = (default)true
applyToPrivate = (default)true
applyToPackage = (default)true

*/

package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameAccessModifiers {

  public interface FirstName {}
  public class SecondName {
    protected class ThirdName {}  // violation 'Name 'ThirdName' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'.'
  }
}

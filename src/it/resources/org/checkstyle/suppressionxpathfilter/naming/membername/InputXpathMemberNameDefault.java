/*
MemberName
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = (default)^[a-z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.membername;

public class InputXpathMemberNameDefault {
     public int num1;
     public int NUM2; // violation 'Name 'NUM2' must match pattern '^[a-z][a-zA-Z0-9]*$'.'

}

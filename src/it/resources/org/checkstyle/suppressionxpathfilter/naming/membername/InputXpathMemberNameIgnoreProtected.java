/*
MemberName
applyToPackage = false
applyToPrivate = (default)true
applyToProtected = false
applyToPublic = (default)true
format = ^m[A-Z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.membername;

public class InputXpathMemberNameIgnoreProtected {

    class Inner {
        public int NUM1; // violation 'Name 'NUM1' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.'
        protected int num2;
    }

}

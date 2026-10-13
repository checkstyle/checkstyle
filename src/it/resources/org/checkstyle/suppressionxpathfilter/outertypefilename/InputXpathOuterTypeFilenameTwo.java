/*
OuterTypeFilename


*/

package org.checkstyle.suppressionxpathfilter.outertypefilename;

class Test { // violation 'The name of the outer type and the file do not match.'
    public interface NestedInterface {}
    public enum NestedEnum {}
    class NestedClass {}
}

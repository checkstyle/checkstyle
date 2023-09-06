/*
OuterTypeFilename


*/

package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

class Class1 { // violation
    public interface NestedInterface {}
    public enum NestedEnum {}
    class NestedClass {}
}

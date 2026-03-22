/*
OuterTypeFilename


*/

package com.puppycrawl.tools.checkstyle.checks.outertypefilename;
// violation below 'The name of the outer type and the file do not match.'
class Class1 {
    public interface NestedInterface {}
    public enum NestedEnum {}
    class NestedClass {}
}

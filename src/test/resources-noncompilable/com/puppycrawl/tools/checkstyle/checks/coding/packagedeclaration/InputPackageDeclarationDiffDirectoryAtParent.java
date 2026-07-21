/*
PackageDeclaration
matchDirectoryStructure = (default)true


*/
// non-compiled with javac: package statement is different than folder
package com.puppycrawl.tools.checkstyle.checks.coding;
// violation above 'Package name is not same as directory.'
public class InputPackageDeclarationDiffDirectoryAtParent {
    public String value;

    private void get(){
    }
}

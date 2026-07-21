/*
PackageDeclaration
matchDirectoryStructure = (default)true


*/
// non-compiled with javac: package statement is different than folder
package com.puppycrawl.tools.checkstyle.checks.coding.packagedeclaration.subpackage;
// violation above 'Package name is not same as directory.'
public class InputPackageDeclarationDiffDirectoryAtSubpackage {
    public String value;

    private void get(){
    }
}

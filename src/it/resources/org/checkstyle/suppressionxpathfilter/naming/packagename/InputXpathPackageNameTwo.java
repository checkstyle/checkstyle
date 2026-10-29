/*
PackageName
format = [A-Z]+


*/

/* test comment */ package
        // violation below "Package name 'org.checkstyle.suppressionxpathfilter.naming.packagename' must match pattern '[A-Z]+'."
        org.checkstyle.
        suppressionxpathfilter.naming.
        packagename;

public interface InputXpathPackageNameTwo {
}

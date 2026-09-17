/*
PackageDeclaration
matchDirectoryStructure = (default)true


*/

// non-compiled with javac: reference to non existent packages

module com.example.app {
    requires java.base;

    exports com.example.api;
}

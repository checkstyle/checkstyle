/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages
module com.example.app {
    requires java.base;

    exports com.example.api;

    // violation below 'All 'requires' directives should be in a single block.'
    requires jdk;
}

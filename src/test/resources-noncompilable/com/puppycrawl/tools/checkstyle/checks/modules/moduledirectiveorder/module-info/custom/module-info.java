/*
ModuleDirectiveOrder
order = requires, uses, provides, exports, opens
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    requires java.base;

    exports com.example.api;

    // violation below ''uses' directive should be before 'exports' directive.'
    uses com.example.api.Service;
}

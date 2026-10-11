/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    uses com.example.api.Service;

    opens com.example.model; // violation ''opens' directive should be before 'uses' directive.'

    exports com.example.api; // violation ''exports' directive should be before 'opens' directive.'

    requires java.base; // violation ''requires' directive should be before 'exports' directive.'
}

/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = false


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    requires java.base;

    requires java.sql;
    exports com.example.api;


    opens com.example.model;
    // violation below 'All 'requires' directives should be in a single block.'
    requires com.example.extra;
    uses com.example.api.Service;
}

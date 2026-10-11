/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    exports com.example.api;

    // violation below ''requires' directive should be before 'exports' directive.'
    requires transitive java.sql;

    // violation below 'Empty line not allowed inside 'requires' directive block.'
    requires static com.example.annotations;
}

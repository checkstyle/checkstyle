/*
ModuleDirectiveOrder
order = requires, exports
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    opens com.example.model;
    exports com.example.api;

    requires java.base; // violation ''requires' directive should be before 'exports' directive.'

    uses com.example.api.Service;
    provides com.example.api.Service with com.example.impl.ServiceImpl;
}

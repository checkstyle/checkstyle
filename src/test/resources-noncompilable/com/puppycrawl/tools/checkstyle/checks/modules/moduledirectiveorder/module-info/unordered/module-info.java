/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    exports com.example.api;

    requires java.base; // violation ''requires' directive should be before 'exports' directive.'

    opens com.example.model;

    uses com.example.api.Service;

    provides com.example.api.Service with com.example.impl.ServiceImpl;
}

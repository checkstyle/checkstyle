/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    requires java.base;
    requires transitive java.sql;
    requires static com.example.annotations;

    exports com.example.api;
    exports com.example.internal to com.example.other;

    opens com.example.model;
    opens com.example.secrets to com.example.friend;

    uses com.example.api.Service;

    provides com.example.api.Service with com.example.impl.ServiceImpl;
}

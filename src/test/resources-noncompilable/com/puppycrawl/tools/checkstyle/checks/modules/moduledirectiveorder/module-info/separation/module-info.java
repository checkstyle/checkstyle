/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    requires java.base;
    exports com.example.api;
    // violation above 'should be separated from the previous block by exactly one empty line'


    opens com.example.model;
    // violation above 'should be separated from the previous block by exactly one empty line'

    uses com.example.api.Service;
    uses com.example.other.Service2;

    provides com.example.api.Service with com.example.impl.ServiceImpl;
}

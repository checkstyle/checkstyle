/*
ModuleDirectiveOrder
order = (default)requires, exports, opens, uses, provides
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    // violation below 'should be separated from the previous block by exactly one empty line'
    requires java.base; exports com.example.api;
}

/*
ModuleDirectiveOrder
order = requires, foo
validateBlockSeparation = (default)true


*/

// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
    requires java.base;
}

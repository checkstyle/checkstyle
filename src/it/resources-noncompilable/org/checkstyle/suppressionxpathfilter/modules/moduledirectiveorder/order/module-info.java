// non-compiled with javac: reference to non existent modules and packages
module com.example.app {
    exports com.example.api;

    requires java.base; // warn
}

// non-compiled with javac: reference to non existent modules and packages
module com.example.app {
    requires java.base;

    exports com.example.api;

    requires jdk; // warn
}

// non-compiled with javac: reference to non existent modules and packages

/**
 * Javadoc for the module.
 */
@Deprecated
open module com.example.myapp {
    requires java.base;
    requires transitive java.sql;
    requires static com.example.annotations;

    exports com.example.api;
    exports com.example.internal to com.example.other, com.example.third;

    opens com.example.model;
    opens com.example.secrets to com.example.friend;

    uses com.example.api.Service;

    provides com.example.api.Service with com.example.impl.ServiceImpl;
    provides com.example.api.Codec with com.example.impl.FastCodec,
        com.example.impl.SafeCodec;
}

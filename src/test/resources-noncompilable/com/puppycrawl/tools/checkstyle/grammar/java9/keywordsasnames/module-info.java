// non-compiled with javac: reference to non-existent modules and packages

// Restricted keywords used as the names inside a module declaration (JLS 3.9).
module module {
    requires requires;
    requires transitive;
    requires transitive transitive;
    requires static requires;
    exports exports;
    exports to to to;
    opens opens to open, with;
    uses uses;
    provides provides with with;
}

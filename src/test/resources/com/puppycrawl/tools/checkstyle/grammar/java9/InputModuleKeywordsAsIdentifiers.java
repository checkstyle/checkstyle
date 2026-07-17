package com.puppycrawl.tools.checkstyle.grammar.java9;

/** All ten module contextual keywords (JLS 3.9) used as ordinary identifiers. */
public class InputModuleKeywordsAsIdentifiers {
    int module;
    int open;
    int requires;
    int transitive;
    int exports;
    int opens;
    int to;
    int uses;
    int provides;
    int with;

    static class open { }

    static class to { }

    void requires(int to) {
        int uses = to + this.requires;
        open: for (int i = 0; i < to; i++) {
            if (uses > 0) {
                break open;
            }
        }
        transitive();
        java.util.function.Supplier<String> s = this::provides;
    }

    void transitive() {
        with(module, open);
    }

    void with(int a, int b) { }

    String provides() {
        return "provides";
    }

    int uses(open o, to t) {
        return to.class.hashCode() + open.class.hashCode();
    }
}

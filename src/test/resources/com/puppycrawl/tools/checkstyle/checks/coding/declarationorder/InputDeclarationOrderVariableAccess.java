package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class InputDeclarationOrderVariableAccess {

    public static final String TYPE = new String("significant_terms");

    private static final Map<String, String> BUCKETS_MAP = Collections.emptyMap();

    public static final InputDeclarationOrderVariableAccess.Stream STREAM =
            new InputDeclarationOrderVariableAccess.Stream() {
        @Override
        public InputDeclarationOrderVariableAccess readResult(InputStream in) throws IOException {
            InputDeclarationOrderVariableAccess buckets = new InputDeclarationOrderVariableAccess();
            buckets.readFrom(in);
            return buckets;
        }
    };

    InputDeclarationOrderVariableAccess() {}

    public void readFrom(InputStream in) throws IOException {
    }

    static class Stream {
        public InputDeclarationOrderVariableAccess readResult(InputStream in) throws IOException {
            return null;
        }
    }
}

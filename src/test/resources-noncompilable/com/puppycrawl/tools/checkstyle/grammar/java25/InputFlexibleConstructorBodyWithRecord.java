// non-compiled with javac: compilable with Java25

package com.puppycrawl.tools.checkstyle.grammar.java25;

import java.util.Objects;

public record InputFlexibleConstructorBodyWithRecord(Object data, int value) {
    public InputFlexibleConstructorBodyWithRecord(Object data) {
        Objects.requireNonNull(data);
        this(data, 42);
    }
    public InputFlexibleConstructorBodyWithRecord(Object data, int value) {
        Objects.requireNonNull(data);
        this.data = data;
        this.value = value;
    }
}

package com.openjdk.checkstyle.test.chapternaming.ruletypevariables;

// violation first line 'Header mismatch*'

import java.util.Map;

public class InputTypeVariablesDosAndDonts {

    public void styleGuideDo() {
        interface SpecialMap<K, V> extends Map<K, V> {
        }

        class GraphMapper<SRC_VERTEX, SRC_EDGE, DST_VERTEX, DST_EDGE> {
        }
    }

    public void styleGuideDonts() {
        interface SpecialMap<Key, Value> extends Map<Key, Value> {
            // 2 violations above:
            // 'Name 'Key' must match pattern'
            // 'Name 'Value' must match pattern'
        }

        class GraphMapper<S, T, U, V> {
            // cannot be determined whether the names are descriptive or not

        }
    }

}

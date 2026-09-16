/*
RightCurly
option = ALONE
tokens = INSTANCE_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

public class InputRightCurlyTestDoubleBrace2 {
}

class Bar2 {{
    int a = 1;
}}
// violation above ''}' at column 1 should be alone on a line.'

abstract class AnotherClass<K, R> implements Collector<K,
        Set<? extends R>, List<R>> {
}

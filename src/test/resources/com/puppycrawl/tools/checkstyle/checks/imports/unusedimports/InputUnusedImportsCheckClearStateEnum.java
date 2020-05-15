package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import net.sf.saxon.type.Type;

public class InputUnusedImportsCheckClearStateEnum {
    enum Type {
        List(0);
        Type(int arg){}
    }

    Type x = Type.List;
}

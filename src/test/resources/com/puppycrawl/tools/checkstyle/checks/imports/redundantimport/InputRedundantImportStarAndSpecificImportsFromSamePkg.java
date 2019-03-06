package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputRedundantImportStarAndSpecificImportsFromSamePkg {
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        list.add("A string");

        System.out.println(list.size());
    }
}

/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = (default)22

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.stream.Collectors;

public class InputUnusedLocalVariableUnnamedTryCatch {
    record Point(double x, double y) {}
    record UniqueRectangle(String id,
                       Point upperLeft, Point lowerRight) {}
    record Caller(String phoneNumber) { }
    static List everyFifthCaller(Queue<Caller> q, int prizes) {
        var winners = new ArrayList<Caller>();
        try {
            while (prizes > 0) {
                Caller _ = q.remove(); // violation, unused local variable '_'
                winners.add(q.remove());
                prizes--;
            }
        } catch (NoSuchElementException _) {

        }
        return winners;
    }

    static void doesFileExist(String path) {
        try (var _ = new FileReader(path)) {
            System.out.println("try");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    static Map getIDs(List<UniqueRectangle> r) {
        return r.stream()
                .collect(
                Collectors.toMap(
                    UniqueRectangle::id,
                    _ -> "NODATA"));
    }
}

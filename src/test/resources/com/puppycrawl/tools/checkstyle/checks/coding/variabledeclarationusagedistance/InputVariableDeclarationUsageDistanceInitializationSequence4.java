/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.ArrayList;
import java.util.List;

public class InputVariableDeclarationUsageDistanceInitializationSequence4 {
    public void tryBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
            System.out.println(list.size());
        } catch (Exception e) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
            System.out.println(list2.size());
        } catch (Exception e) {
        }
    }

    public void catchBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
        } catch (Exception e) {
            System.out.println(list.size());
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
        } catch (Exception e) {
            System.out.println(list2.size());
        }
    }

    public void finallyBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
        } catch (Exception e) {
        } finally {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
        } catch (Exception e) {
        } finally {
            System.out.println(list2.size());
        }
    }

    public void tryResource() {
        List<Integer> list = new ArrayList<>();
        this.getAutoCloseable(0);
        try (AutoCloseable a = this.getAutoCloseable(list.size())) {
        } catch (Exception e) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try (AutoCloseable a = new Close(list2.size())) {
        } catch (Exception e) {
        }
    }

    private void nothing() {
    }

    private class Close implements AutoCloseable {
        public Close(int i) {

        }

        @Override
        public void close() throws Exception {

        }
    }

    private boolean check() {
        return true;
    }

    private AutoCloseable getAutoCloseable(int i) {
        return new Close(i);
    }
}

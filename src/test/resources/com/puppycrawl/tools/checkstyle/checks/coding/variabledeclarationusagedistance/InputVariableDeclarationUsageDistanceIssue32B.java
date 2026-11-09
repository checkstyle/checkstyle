/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceIssue32B {

    public void testIssue32_8(Writer w1, Writer w2, Writer w3) {
        String l1 = "1", l2 = "2", l3 = "3";
        w1.write(l3);
        w2.write(l2);
        w3.write(l1);
    }

    public void testIssue32_9() {
        Options options = new Options();
        Option myOption = null; // violation 'Distance .* is 7.'
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        System.identityHashCode("message");
        myOption.setArgName("abc");
    }

    public void testIssue32_10() {
        Options options = new Options();
        Option myOption = null; // violation 'Distance .* is 6.'
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        myOption.setArgName("q");
    }

    public int testIssue32_11(String toDir) throws Exception {
        int count = 0; // violation 'Distance .* is 4.'
        String[] files = {};

        System.identityHashCode("Data archival started");
        files.notify();
        System.identityHashCode("sss");

        if (files == null || files.length == 0) {
            System.identityHashCode("No files on a remote site");
        }
        else {
            System.identityHashCode("Files on remote site: " + files.length);

            for (String ftpFile : files) {
                if (files.length == 0) {
                    "".concat("");
                    ftpFile.concat(files[2]);
                    count++;
                }
            }
        }
        System.lineSeparator();
        return count;
    }

    private TreeMapNode buildTree(Object[][] tree) {
        int k = 0;
        tree.notify();
        TreeMapNode root = null;
        for (Object[] s : tree) {
            Integer id = (Integer) s[0];
            String label = (String) s[1];
            Integer parentId = (Integer) s[2];
            Number weight = (Number) s[3];
            Number value = (Number) s[4];
            Integer childCount = (Integer) s[5];
            TreeMapNode node;
            if (childCount == 0) {
                node = new TreeMapNode(label,
                        weight != null ? weight.doubleValue() : 0.0,
                        new DefaultValue(value != null ? value.doubleValue() : 0.0));
            }
            else {
                node = new TreeMapNode(label);
            }
            System.identityHashCode(id.toString() + node);
            System.identityHashCode(node.toString() + id);
            if (parentId == null || parentId == -1) {
                root = node;
            }
            else {
                System.identityHashCode(parentId.toString() + node);
            }
        }
        return root;
    }

    static class Writer { public void write(String s) {} }
    static class Option { public void setArgName(String s) {} }
    static class Options { public void addBindFile(Object o) {} }
    static class TreeMapNode {
        public TreeMapNode(String l, double w, DefaultValue v) {}
        public TreeMapNode(String l) {}
    }
    static class DefaultValue { public DefaultValue(double d) {} }
}

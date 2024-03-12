/*
InnerAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class InputInnerAssignment
{
    void innerAssignments()
    {
        int a;
        int b;
        int c;

        a = b = c = 1; // 2 violations

        String s = Integer.toString(b = 2); // violation

        Integer i = new Integer(a += 5); // violation

        c = b++; // common practice, don't flag
                 // even though technically an assignment to b

        for (int j = 0; j < 6; j += 2) { // common practice, don't flag
            a += j;
        }
    }

    public static void demoInputStreamIdiom(java.io.InputStream is) throws java.io.IOException
    {
        int b;
        while ((b = is.read()) != -1) // common idiom to avoid clumsy loop control logic don't flag
        {
            // work with b
        }
    }

    public static void demoNoBrace()
    {
        // code that doesn't contain braces around conditional code
        // results in a parse tree without SLISTs
        // no assignment should be flagged here
        int sum = 0;

        for (int i = 0; i < 3; i++)
            sum = sum + i;

        if (sum > 4)
            sum += 2;
        else if (sum < 2)
            sum += 1;
        else
            sum += 100;

        while (sum > 4)
            sum -= 1;

        do
            sum = sum + 1;
        while (sum < 6);

        ChildParent o = new ChildParent();
        Object t = null;

        while (o != null)
            t = o = o.getParent(); // violation
    }

    @SuppressWarnings(value = "unchecked")
    public java.util.Collection<Object> allParams() {
        java.util.ArrayList params = new java.util.ArrayList();
        params.add("one");
        params.add("two");
        return params;
    }

    // Taken from JDK7 java.lang.Package src code.
    private static Manifest loadManifest(String fn) {
        try (FileInputStream fis = new FileInputStream(fn);
             JarInputStream jis = new JarInputStream(fis, false))
        {
            return jis.getManifest();
        } catch (IOException e)
        {
            return null;
        }
    }

    private static class ChildParent {
        public ChildParent getParent() {
            return this;
        }
    }
}

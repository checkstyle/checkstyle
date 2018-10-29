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

        a = b = c = 1; // flag two inner assignments

        String s = Integer.toString(b = 2); // flag inner assignment

        Integer i = new Integer(a += 5); // flag inner assignment

        c = b++; // common practice, don't flag
                 // even though technically an assignment to b

        for (int j = 0; j < 6; j += 2) { // common practice, don't flag
            a += j;
        }
    }

    public void demoBug1195047Comment3()
    {
        // inner assignment should flag all assignments to b or bb but none of those to i or j
        int y = 1;
        int b = 0;
        boolean bb;
        int i;

        if (bb = false) {}
        for (i = 0; bb = false; i = i + 1) {}
        while (bb = false) {}
        if ((bb = false)) {}
        for (int j = 0; (bb = false); j += 1) {}
        while ((bb = false)) {}
        i = (bb = false) ? (b = 2) : (b += 1);
        i = (b += 1) + (b -= 1);
        do {i += 1;} while (bb = false);
    }

    public static void demoInputStreamIdiom(java.io.InputStream is) throws java.io.IOException
    {
        int b;
        while ((b = is.read()) != -1) // common idiom to avoid clumsy loop control logic, don't flag (make configurable later)
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
}

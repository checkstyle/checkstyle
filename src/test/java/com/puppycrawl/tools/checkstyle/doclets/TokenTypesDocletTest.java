////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.doclets;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sun.javadoc.RootDoc;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javadoc.JavadocTool;
import com.sun.tools.javadoc.Messager;
import com.sun.tools.javadoc.ModifierFilter;

public class TokenTypesDocletTest {
    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/doclets/" + filename;
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(TokenTypesDoclet.class, true);
    }

    @Test
    public void testOptionLength() {
        // optionLength returns 2 for option "-destfile"
        assertEquals(2, TokenTypesDoclet.optionLength("-destfile"));

        // optionLength returns 0 for options different from "-destfile"
        assertEquals(0, TokenTypesDoclet.optionLength("-anyOtherOption"));
    }

    @Test
    public void testCheckOptions() {
        final Context context = new Context();
        final TestMessager testMessager = new TestMessager(context);

        //pass invalid options - empty array
        final String[][] options = new String[3][1];
        assertFalse(TokenTypesDoclet.checkOptions(options, testMessager));

        //pass valid options - array with one "-destfile" option
        options[0][0] = "-destfile";
        assertTrue(TokenTypesDoclet.checkOptions(options, testMessager));

        //pass invalid options - array with more than one "-destfile" option
        options[1][0] = "-destfile";
        assertFalse(TokenTypesDoclet.checkOptions(options, testMessager));

        final String[] expected = {
            "Usage: javadoc -destfile file -doclet TokenTypesDoclet ...",
            "Only one -destfile option allowed.",
        };

        Assert.assertArrayEquals(expected, testMessager.messages.toArray());
    }

    @Test
    public void testNotConstants() throws Exception {
        // Token types must be public int constants, which means that they must have
        // modifiers public, static, final and type int, because they are referenced statically in
        // a lot of different places, must not be changed and an int value is used to encrypt
        // a token type.
        final ListBuffer<String[]> options = new ListBuffer<>();
        options.add(new String[] {"-doclet", "TokenTypesDoclet"});
        options.add(new String[] {"-destfile", "target/tokentypes.properties"});

        final ListBuffer<String> names = new ListBuffer<>();
        names.add(getPath("InputTokenTypesDocletNotConstants.java"));

        final Context context = new Context();
        new TestMessager(context);
        final JavadocTool javadocTool = JavadocTool.make0(context);
        final RootDoc rootDoc = getRootDoc(javadocTool, options, names);

        assertTrue(TokenTypesDoclet.start(rootDoc));
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        final ListBuffer<String[]> options = new ListBuffer<>();
        options.add(new String[] {"-destfile", "target/tokentypes.properties"});

        final ListBuffer<String> names = new ListBuffer<>();
        names.add(getPath("InputTokenTypesDocletEmptyJavadoc.java"));

        final Context context = new Context();
        new TestMessager(context);
        final JavadocTool javadocTool = JavadocTool.make0(context);
        final RootDoc rootDoc = getRootDoc(javadocTool, options, names);

        try {
            TokenTypesDoclet.start(rootDoc);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            // Token types must have first sentence of Javadoc summary
            // so that a brief description could be provided. Otherwise,
            // an IllegalArgumentException is thrown.
        }
    }

    @Test
    public void testCorrect() throws Exception {
        final ListBuffer<String[]> options = new ListBuffer<>();
        options.add(new String[] {"-destfile", "target/tokentypes.properties"});

        final ListBuffer<String> names = new ListBuffer<>();
        names.add(getPath("InputTokenTypesDocletCorrect.java"));

        final Context context = new Context();
        new TestMessager(context);
        final JavadocTool javadocTool = JavadocTool.make0(context);
        final RootDoc rootDoc = getRootDoc(javadocTool, options, names);

        assertTrue(TokenTypesDoclet.start(rootDoc));
        final String fileContent =
                FileUtils.readFileToString(new File("target/tokentypes.properties"),
                        StandardCharsets.UTF_8);
        assertTrue("File content is not expected",
                fileContent.startsWith("EOF=The end of file token."));

    }

    private static RootDoc getRootDoc(JavadocTool javadocTool, ListBuffer<String[]> options,
            ListBuffer<String> names) throws Exception {
        final Method getRootDocImpl = getMethodGetRootDocImplByReflection();
        final RootDoc rootDoc;
        if (System.getProperty("java.version").startsWith("1.7.")) {
            rootDoc = (RootDoc) getRootDocImpl.invoke(javadocTool, "", "UTF-8",
                    new ModifierFilter(ModifierFilter.ALL_ACCESS),
                    names.toList(),
                    options.toList(),
                    false,
                    new ListBuffer<String>().toList(),
                    new ListBuffer<String>().toList(),
                    false, false, false);
        }
        else {
            rootDoc = (RootDoc) getRootDocImpl.invoke(javadocTool, "", "UTF-8",
                    new ModifierFilter(ModifierFilter.ALL_ACCESS),
                    names.toList(),
                    options.toList(),
                    new ListBuffer<JavaFileObject>().toList(),
                    false,
                    new ListBuffer<String>().toList(),
                    new ListBuffer<String>().toList(),
                    false, false, false);
        }
        return rootDoc;
    }

    private static Method getMethodGetRootDocImplByReflection() throws ClassNotFoundException {
        Method result = null;
        final Class<?> javadocToolClass = Class.forName("com.sun.tools.javadoc.JavadocTool");
        final Method[] methods = javadocToolClass.getMethods();
        for (Method method: methods) {
            if ("getRootDocImpl".equals(method.getName())) {
                result = method;
            }
        }
        return result;
    }

    private static class TestMessager extends Messager {

        private final List<String> messages = new ArrayList<>();

        TestMessager(Context context) {
            super(context, "");
        }

        @Override
        public void printError(String message) {
            messages.add(message);
        }
    }
}

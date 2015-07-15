////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.DetailAstRootHolder;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class SuppressionAnnotationFilterTest extends BaseCheckTestSupport {
    private static String[] sAllMessages = {
        "14:11: Missing a Javadoc comment.",
        "21:5: Missing a Javadoc comment.",
        "22:5: Missing a Javadoc comment.",
        "35:3: Missing a Javadoc comment.",
        "40: Expected an @return tag.",
        "41:9: Name 'FOO' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "52:9: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "55:11: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "56:7: Missing a Javadoc comment.",
        "56:12: Name 'TESTCLASS' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "64:5: Missing a Javadoc comment.",
        "64:10: Name 'FOO1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "64:48: Name 'A3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "67:9: Name 'A4' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "70:9: Name 'A5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "70:24: Name 'A6' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "70:32: Name 'A7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "74:43: Name 'A8' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "83:9: Name 'A9' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "90:9: Name 'B1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "93:9: Name 'B2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "96:9: Name 'B3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "99:9: Name 'B4' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "104:9: Name 'B5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "108:9: Name 'B6' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "112:9: Name 'B7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "117:9: Name 'B8' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "121:9: Name 'B9' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "125:9: Name 'B10' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "131:22: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "135: Annotation 'Deprecated' should be alone on line.",
        "135:5: Missing a Javadoc comment.",
        "139:10: Name 'FOO3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "146: Expected an @return tag.",
        "146:9: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "146:25: Expected @param tag for 'C1'.",
        "146:25: Name 'C1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "153:9: Name 'C2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "153:13: Missing a Javadoc comment.",
        "153:24: Name 'FOO3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "153:34: Name 'C3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "162:10: Unused @param tag for 'it'.",
        "169:7: Missing a Javadoc comment.",
        "171:21: Name 'C5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
    };

    @Test
    public void testNone()
            throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testNamesParsing1()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", "@Test1");
        filterConfig.addAttribute("annotationName", "@com.puppycrawl.tools.checkstyle.filters.Test2");
        filterConfig.addAttribute("annotationName", "Test3");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.Test4");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.InputSuppressionAnnotation");
        final String[] resulted = {
            "14:11: Missing a Javadoc comment.",
            "21:5: Missing a Javadoc comment.",
            "22:5: Missing a Javadoc comment.",
            "35:3: Missing a Javadoc comment.",
            "40: Expected an @return tag.",
            "64:5: Missing a Javadoc comment.",
            "64:10: Name 'FOO1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "70:32: Name 'A7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "125:9: Name 'B10' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "131:22: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "135: Annotation 'Deprecated' should be alone on line.",
            "135:5: Missing a Javadoc comment.",
            "146: Expected an @return tag.",
            "146:9: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "162:10: Unused @param tag for 'it'.",
            "171:21: Name 'C5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(createChecker(filterConfig),
                getPath("filters/InputSuppressionAnnotationtFilter.java"), resulted);
    }

    @Test
    public void testNamesParsing2()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", "@Test1");
        filterConfig.addAttribute("annotationName", "@com.puppycrawl.tools.checkstyle.filters.Test2");
        filterConfig.addAttribute("annotationName", "Test3");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.Test4");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.InputSuppressionAnnotation");
        final String[] resulted = {
            "14:11: Missing a Javadoc comment.",
            "21:5: Missing a Javadoc comment.",
            "22:5: Missing a Javadoc comment.",
            "35:3: Missing a Javadoc comment.",
            "40: Expected an @return tag.",
            "64:5: Missing a Javadoc comment.",
            "64:10: Name 'FOO1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "70:32: Name 'A7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "117:9: Name 'B8' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "125:9: Name 'B10' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "131:22: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "135: Annotation 'Deprecated' should be alone on line.",
            "135:5: Missing a Javadoc comment.",
            "146: Expected an @return tag.",
            "146:9: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "162:10: Unused @param tag for 'it'.",
            "171:21: Name 'C5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(createChecker(filterConfig),
                getPath("filters/InputSuppressionAnnotationtFilter.java"), resulted);
    }

    @Test
    public void testModifiersExcluded()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", "@Test1");
        filterConfig.addAttribute("annotationName", "@com.puppycrawl.tools.checkstyle.filters.Test2");
        filterConfig.addAttribute("annotationName", "Test3");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.Test4");
        filterConfig.addAttribute("annotationName", "InputSuppressionAnnotation");
        filterConfig.addAttribute("modifiersExcluded", "false");
        final String[] resulted = {
            "14:11: Missing a Javadoc comment.",
            "21:5: Missing a Javadoc comment.",
            "22:5: Missing a Javadoc comment.",
            "64:5: Missing a Javadoc comment.",
            "64:10: Name 'FOO1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "70:32: Name 'A7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "125:9: Name 'B10' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "131:22: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "146: Expected an @return tag.",
            "146:9: Name 'FOO2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "162:10: Unused @param tag for 'it'.",
            "171:21: Name 'C5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(createChecker(filterConfig),
                getPath("filters/InputSuppressionAnnotationtFilter.java"), resulted);
    }

    @Test
    public void testRegEx1()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", "@Test1");
        filterConfig.addAttribute("annotationName", "@com.puppycrawl.tools.checkstyle.filters.Test2");
        filterConfig.addAttribute("annotationName", "Test3");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.Test4");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.InputSuppressionAnnotation");
        filterConfig.addAttribute("permittedChecks", ".*");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testRegEx2()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", "@Test1");
        filterConfig.addAttribute("annotationName", "@com.puppycrawl.tools.checkstyle.filters.Test2");
        filterConfig.addAttribute("annotationName", "Test3");
        filterConfig.addAttribute("annotationName", "com.puppycrawl.tools.checkstyle.filters.Test4");
        filterConfig.addAttribute("annotationName", "InputSuppressionAnnotation");
        filterConfig.addAttribute("modifiersExcluded", "false");
        filterConfig.addAttribute("permittedChecks", ".*Name.*");
        filterConfig.addAttribute("permittedChecks", ".*Javadoc.*");
        final String[] suppressed = {
            "135: Annotation 'Deprecated' should be alone on line.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test(expected = CheckstyleException.class)
    public void testThrowWrongRegexp()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("permittedChecks", "*\\.*");
        createChecker(filterConfig);
    }

    @Test(expected = CheckstyleException.class)
    public void testThrowBadAnnotationName()
            throws Exception {
        final DefaultConfiguration filterConfig =
                createFilterConfig(SuppressionAnnotationFilter.class);
        filterConfig.addAttribute("annotationName", ".Test1");
        createChecker(filterConfig);
    }

    @Test
    public void testMisc()
            throws Exception {
        final SuppressionAnnotationFilter test = new SuppressionAnnotationFilter();
        final String[] dummyArray = {
        };
        Assert.assertArrayEquals(dummyArray, test.getPermittedChecks());
        Assert.assertArrayEquals(dummyArray, test.getAnnotationName());
        final AuditEvent noMessageEvent = new AuditEvent(test, "abc", null);
        Assert.assertTrue(test.accept(noMessageEvent));
        final LocalizedMessage dummyMessage =
            new LocalizedMessage(0, 0, null, null, dummyArray, null, null, null, null);
        final AuditEvent noTreeEvent = new AuditEvent(test, "abc", dummyMessage);
        // Here abortive branch of accept method with no AST present is tested.
        Assert.assertTrue(test.accept(noTreeEvent));
    }

    public static DefaultConfiguration createFilterConfig(Class<?> aClass) {
        return new DefaultConfiguration(aClass.getName());
    }

    protected void verifySuppressed(Configuration aFilterConfig,
            String[] aSuppressed)
                    throws Exception {
        verify(createChecker(aFilterConfig),
                getPath("filters/InputSuppressionAnnotationtFilter.java"),
                removeSuppressed(sAllMessages, aSuppressed));
    }

    @Override
    protected Checker createChecker(Configuration aFilterConfig)
            throws CheckstyleException, UnsupportedEncodingException {
        final DefaultConfiguration checkerConfig =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createCheckConfig(TreeWalker.class);
        checksConfig.addChild(createCheckConfig(DetailAstRootHolder.class));
        checksConfig.addChild(createCheckConfig(MemberNameCheck.class));
        checksConfig.addChild(createCheckConfig(MethodNameCheck.class));
        checksConfig.addChild(createCheckConfig(ParameterNameCheck.class));
        checksConfig.addChild(createCheckConfig(ConstantNameCheck.class));
        checksConfig.addChild(createCheckConfig(JavadocMethodCheck.class));
        checksConfig.addChild(createCheckConfig(AnnotationLocationCheck.class));
        checkerConfig.addChild(checksConfig);
        if (aFilterConfig != null) {
            checkerConfig.addChild(aFilterConfig);
        }
        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));
        return checker;
    }

    private String[] removeSuppressed(String[] from, String[] remove) {
        final Collection<String> coll =
                Lists.newArrayList(Arrays.asList(from));
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(new String[coll.size()]);
    }
}

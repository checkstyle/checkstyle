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

package com.google.checkstyle.test.base;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class BaseCheckTestSupport {
    protected final Properties props = new Properties();
    final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    protected static DefaultConfiguration createCheckConfig(Class<?> aClazz) {
        return new DefaultConfiguration(aClazz.getName());
    }

    protected Checker createChecker(Configuration aCheckConfig)
        throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(aCheckConfig);
        final Checker checker = new Checker();
        // make sure the tests always run with english error messages
        // so the tests don't fail in supported locales like german
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefLogger(stream));
        return checker;
    }

    protected DefaultConfiguration createCheckerConfig(Configuration aConfig) {
        final DefaultConfiguration dc =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", "iso-8859-1");
        dc.addChild(twConf);
        twConf.addChild(aConfig);
        return dc;
    }

    protected static String getPath(String aFilename)
        throws IOException {
        return new File("src/main/java/com/google/checkstyle/test/filebasic/" + aFilename)
            .getCanonicalPath();
    }

    protected static String getSrcPath(String aFilename) throws IOException {

        return new File("src/test/java/com/puppycrawl/tools/checkstyle/" + aFilename)
            .getCanonicalPath();
    }

    protected void verify(Configuration aConfig, String aFileName, String[] aExpected,
            Integer... aWarnsExpected) throws Exception {
        verify(createChecker(aConfig), aFileName, aFileName, aExpected, aWarnsExpected);
    }

    protected void verify(Checker aC, String aFileName, String[] aExpected,
            Integer... aWarnsExpected) throws Exception {
        verify(aC, aFileName, aFileName, aExpected, aWarnsExpected);
    }

    private void verify(Checker aC,
            String aProcessedFilename,
            String aMessageFileName,
            String[] aExpected, Integer... aWarnsExpected)
        throws Exception {
        verify(aC,
            new File[] {new File(aProcessedFilename)},
            aMessageFileName, aExpected, aWarnsExpected);
    }

    void verify(Checker aC,
            File[] aProcessedFiles,
            String aMessageFileName,
            String[] aExpected,
            Integer... aWarnsExpected)
        throws Exception {
        stream.flush();
        final List<File> theFiles = Lists.newArrayList();
        Collections.addAll(theFiles, aProcessedFiles);
        final int errs = aC.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream localStream =
            new ByteArrayInputStream(stream.toByteArray());
        try (final LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(localStream, StandardCharsets.UTF_8))) {

            for (int i = 0; i < aExpected.length; i++) {
                final String expected = aMessageFileName + ":" + aExpected[i];
                final String actual = lnr.readLine();
                assertEquals("error message " + i, expected, actual);
                String parseInt = removeDeviceFromPathOnWindows(actual);
                parseInt = parseInt.substring(parseInt.indexOf(':') + 1);
                parseInt = parseInt.substring(0, parseInt.indexOf(':'));
                final int lineNumber = Integer.parseInt(parseInt);
                Integer integer = 0;
                if (Arrays.asList(aWarnsExpected).contains(lineNumber)) {
                    integer = lineNumber;
                }
                assertEquals("error message " + i, (long) integer, lineNumber);
            }

            assertEquals("unexpected output: " + lnr.readLine(),
                    aExpected.length, errs);
        }
        aC.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey
     *            the key of message in 'messages.properties' file.
     */
    protected String getCheckMessage(Class<? extends AbstractViolationReporter> aClass,
            String messageKey) {
        final Properties pr = new Properties();
        try {
            pr.load(aClass.getResourceAsStream("messages.properties"));
        }
        catch (IOException ignored) {
            return null;
        }
        return pr.getProperty(messageKey);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties' file.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     */
    protected String getCheckMessage(Class<? extends AbstractViolationReporter> aClass,
            String messageKey, Object... arguments) {
        final MessageFormat formatter = new MessageFormat(getCheckMessage(aClass, messageKey),
                Locale.ROOT);
        return formatter.format(arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties' file.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     */
    protected String getCheckMessage(Map<String, String> messages, String messageKey,
            Object... arguments) {
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            if (messageKey.equals(entry.getKey())) {
                final MessageFormat formatter = new MessageFormat(entry.getValue(), Locale.ROOT);
                return formatter.format(arguments);
            }
        }
        return null;
    }

    private static String removeDeviceFromPathOnWindows(String path) {
        final String os = System.getProperty("os.name", "Unix");
        if (os.startsWith("Windows")) {
            return path.substring(path.indexOf(':') + 1);
        }
        return path;
    }
}

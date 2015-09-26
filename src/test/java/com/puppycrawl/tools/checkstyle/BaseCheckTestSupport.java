package com.puppycrawl.tools.checkstyle;

import static java.text.MessageFormat.format;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class BaseCheckTestSupport {
    /**
     * A brief logger that only display info about errors.
     */
    protected static class BriefLogger
            extends DefaultLogger {
        public BriefLogger(OutputStream out) {
            super(out, true, out, false, false);
        }

        public BriefLogger(OutputStream out, boolean printSeverity) {
            super(out, true, out, false, printSeverity);
        }

        @Override
        public void auditStarted(AuditEvent evt) {
        }

    }

    protected final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    protected static DefaultConfiguration createCheckConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    protected Checker createChecker(Configuration checkConfig)
            throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker checker = new Checker();
        // make sure the tests always run with default error messages (language-invariant)
        // so the tests don't fail in supported locales like German
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefLogger(stream));
        return checker;
    }

    protected Checker createChecker(Configuration checkConfig, boolean printSeverity)
        throws Exception {

        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker checker = new Checker();
        // make sure the tests always run with english error messages
        // so the tests don't fail in supported locales like german
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefLogger(stream, printSeverity));
        return checker;
    }

    protected DefaultConfiguration createCheckerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", "UTF-8");
        dc.addChild(twConf);
        twConf.addChild(config);
        return dc;
    }

    protected static String getPath(String filename)
            throws IOException {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename).getCanonicalPath();
    }

    protected static String getUriString(String filename) {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename).toURI().toString();
    }

    protected static String getSrcPath(String filename) throws IOException {

        return new File("src/test/java/com/puppycrawl/tools/checkstyle/" + filename).getCanonicalPath();
    }

    protected void verify(Configuration aConfig, String fileName, String... expected)
            throws Exception {
        verify(createChecker(aConfig), fileName, fileName, expected);
    }

    protected void verify(Configuration aConfig, boolean printSeverity,
                          String filename, String... expected) throws Exception {
        verify(createChecker(aConfig, printSeverity), filename, filename, expected);
    }

    protected void verify(Checker checker, String fileName, String... expected)
            throws Exception {
        verify(checker, fileName, fileName, expected);
    }

    protected void verify(Checker checker,
                          String processedFilename,
                          String messageFileName,
                          String... expected)
            throws Exception {
        verify(checker,
                new File[]{new File(processedFilename)},
                messageFileName, expected);
    }

    protected void verify(Checker checker,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        stream.flush();
        final List<File> theFiles = Lists.newArrayList();
        Collections.addAll(theFiles, processedFiles);
        final int errs = checker.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
        try (final LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = messageFileName + ":" + expected[i];
                final String actual = lnr.readLine();
                assertEquals("error message " + i, expectedResult, actual);
            }

            assertEquals("unexpected output: " + lnr.readLine(),
                    expected.length, errs);
        }
        checker.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments  the arguments of message in 'messages.properties' file.
     */
    protected String getCheckMessage(String messageKey, Object... arguments) {
        Properties pr = new Properties();
        try {
            pr.load(getClass().getResourceAsStream("messages.properties"));
        } catch (IOException e) {
            return null;
        }
        return format(pr.getProperty(messageKey), arguments);
    }
}

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
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public abstract class BaseCheckTestSupport {
    /**
     * a brief logger that only display info about errors
     */
    protected static class BriefLogger
            extends DefaultLogger {
        public BriefLogger(OutputStream out) throws UnsupportedEncodingException {
            super(out, true);
        }

        @Override
        public void auditStarted(AuditEvent evt) {
        }

        @Override
        public void fileFinished(AuditEvent evt) {
        }

        @Override
        public void fileStarted(AuditEvent evt) {
        }
    }

    protected final ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
    protected final PrintStream stream = new PrintStream(BAOS);
    protected final Properties props = new Properties();

    public static DefaultConfiguration createCheckConfig(Class<?> clazz) {
        final DefaultConfiguration checkConfig =
                new DefaultConfiguration(clazz.getName());
        return checkConfig;
    }

    protected Checker createChecker(Configuration checkConfig)
            throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker c = new Checker();
        // make sure the tests always run with default error messages (language-invariant)
        // so the tests don't fail in supported locales like German
        final Locale locale = Locale.ROOT;
        c.setLocaleCountry(locale.getCountry());
        c.setLocaleLanguage(locale.getLanguage());
        c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        c.configure(dc);
        c.addListener(new BriefLogger(stream));
        return c;
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

    protected static String getSrcPath(String filename) throws IOException {

        return new File("src/test/java/com/puppycrawl/tools/checkstyle/" + filename).getCanonicalPath();
    }

    protected void verify(Configuration aConfig, String fileName, String... expected)
            throws Exception {
        verify(createChecker(aConfig), fileName, fileName, expected);
    }

    protected void verify(Checker c, String fileName, String... expected)
            throws Exception {
        verify(c, fileName, fileName, expected);
    }

    protected void verify(Checker c,
                          String processedFilename,
                          String messageFileName,
                          String... expected)
            throws Exception {
        verify(c,
                new File[]{new File(processedFilename)},
                messageFileName, expected);
    }

    protected void verify(Checker c,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        stream.flush();
        final List<File> theFiles = Lists.newArrayList();
        Collections.addAll(theFiles, processedFiles);
        final int errs = c.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream bais =
                new ByteArrayInputStream(BAOS.toByteArray());
        final LineNumberReader lnr =
                new LineNumberReader(new InputStreamReader(bais));

        for (int i = 0; i < expected.length; i++) {
            final String expectedResult = messageFileName + ":" + expected[i];
            final String actual = lnr.readLine();
            assertEquals("error message " + i, expectedResult, actual);
        }

        assertEquals("unexpected output: " + lnr.readLine(),
                expected.length, errs);
        c.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments  the arguments of message in 'messages.properties' file.
     */
    public String getCheckMessage(String messageKey, Object... arguments) {
        Properties pr = new Properties();
        try {
            pr.load(getClass().getResourceAsStream("messages.properties"));
        } catch (IOException e) {
            return null;
        }
        return format(pr.getProperty(messageKey), arguments);
    }
}

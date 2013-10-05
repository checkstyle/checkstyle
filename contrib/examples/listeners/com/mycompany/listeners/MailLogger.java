package com.mycompany.listeners;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 *  Buffers log messages from DefaultLogger, and sends an e-mail with the
 *  results. The following Project properties are used to send the mail.
 *  <ul>
 *    <li> MailLogger.mailhost [default: localhost] - Mail server to use</li>
 *
 *    <li> MailLogger.from [required] - Mail "from" address</li>
 *    <li> MailLlistener.failure.notify [default: true] - Send build failure
 *    e-mails?</li>
 *    <li> MailLogger.success.notify [default: true] - Send build success
 *    e-mails?</li>
 *    <li> MailLogger.failure.to [required if failure mail to be sent] - Address
 *    to send failure messages to</li>
 *    <li> MailLogger.success.to [required if success mail to be sent] - Address
 *    to send success messages to</li>
 *    <li> MailLogger.failure.subject [default: "Build Failure"] - Subject of
 *    failed build</li>
 *    <li> MailLlistener.success.subject [default: "Build Success"] - Subject of
 *    successful build</li>
 *  </ul>
 *  These properties are set using standard property setting mechanisms
 *  (command-line -D, ant &lt;property&gt;, etc).Properties can be overridden
 *  by specifying the filename of a properties file in the <i>
 *  MailLogger.properties.file property</i> . Any properties defined in that
 *  file will override properties.
 * Based on
 * <a href="http://ant.apache.org/index.html">org.apache.tools.ant.listener.MailLogger>org.apache.tools.ant.listener.MailLogger</a>
 * @author Erik Hatcher
 *         <a href="mailto:ehatcher@apache.org">ehatcher@apache.org</a>
 * @author Rick Giles
 */
public class MailLogger
    implements AuditListener
{
    /** output stream for logger */
    private ByteArrayOutputStream mOutputStream;

    /** adapted listener */
    private DefaultLogger mLogger;

    /** count of the number of errors and exceptions */
    private int mErrors;

    /**
     * Constructs a <code>MailLogger</code>
     */
    public MailLogger()
    {
        mOutputStream = new ByteArrayOutputStream();
        mLogger = new DefaultLogger(mOutputStream, false);
        mErrors = 0;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditStarted(AuditEvent aEvt)
    {
        mLogger.auditStarted(aEvt);
    }

    /**
     * Sends an e-mail with the log results.
     * @see com.puppycrawl.tools.checkstyle.api.AuditListener
     */
    public void auditFinished(AuditEvent aEvt)
    {
        mLogger.auditFinished(aEvt);

        final Properties properties = System.getProperties();

        // overlay specified properties file (if any), which overrides project
        // settings
        final Properties fileProperties = new Properties();
        final String filename =
            (String) properties.get("MailLogger.properties.file");
        if (filename != null) {
            InputStream is = null;
            try {
                is = new FileInputStream(filename);
                fileProperties.load(is);
            }
            catch (IOException ioe) {
                // ignore because properties file is not required
                ;
            }
            finally {
                if (is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException e) {
                        ;
                    }
                }
            }
        }

        for (Enumeration e = fileProperties.keys(); e.hasMoreElements();) {
            final String key = (String) e.nextElement();
            final String value = fileProperties.getProperty(key);
            properties.put(key, value);
        }

        final boolean success = (mErrors == 0);
        final String prefix = success ? "success" : "failure";

        try {
            final String mailhost =
                getValue(properties, "mailhost", "localhost");
            final String from = getValue(properties, "from", null);

            final String toList = getValue(properties, prefix + ".to", null);
            final String subject = getValue(properties, prefix + ".subject",
                    (success)
                        ? "Checkstyle Audit Success"
                        : "Checkstyle Audit Failure");

            sendMail(mailhost, from, toList, subject, mOutputStream.toString());
        }
        catch (Exception e) {
            System.out.println("MailLogger failed to send e-mail!");
            e.printStackTrace(System.err);
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileStarted(AuditEvent aEvt)
    {
        mLogger.fileStarted(aEvt);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileFinished(AuditEvent aEvt)
    {
        mLogger.fileFinished(aEvt);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addError(AuditEvent aEvt)
    {
        if (SeverityLevel.ERROR.equals(aEvt.getSeverityLevel())) {
            mLogger.addError(aEvt);
            mErrors++;
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        mLogger.addException(aEvt, aThrowable);
        mErrors++;
    }

    /**
     *  Gets the value of a property.
     *
     * @param  aProperties Properties to obtain value from.
     * @param  aName suffix of property name. "MailLogger." will be
     * prepended internally.
     * @param  aDefaultValue value returned if not present in the properties.
     * Set to null to make required.
     * @return The value of the property, or default value.
     * @throws CheckstyleException if no default value is specified and the
     *  property is not present in properties.
     */
    private String getValue(Properties aProperties, String aName,
                            String aDefaultValue) throws CheckstyleException
    {
        final String propertyName = "MailLogger." + aName;
        String value = (String) aProperties.get(propertyName);

        if (value == null) {
            value = aDefaultValue;
        }

        if (value == null) {
            throw new CheckstyleException(
                "Missing required parameter: " + propertyName);
        }

        return value;
    }
    /**
     *  Send the mail
     *
     * @param  aMailhost mail server
     * @param  aFrom from address
     * @param  aToList comma-separated recipient list
     * @param  aSubject mail subject
     * @param  aText mail body
     * @throws Exception if sending message fails
     */
    private void sendMail(String aMailhost, String aFrom, String aToList,
            String aSubject, String aText)
        throws Exception
    {
        // Get system properties
        final Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", aMailhost);

        // Get session
        final Session session = Session.getDefaultInstance(props, null);

        // Define message
        final MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(aFrom));
        final StringTokenizer t = new StringTokenizer(aToList, ", ", false);
        while (t.hasMoreTokens()) {
            message.addRecipient(
                MimeMessage.RecipientType.TO,
                new InternetAddress(t.nextToken()));
        }
        message.setSubject(aSubject);
        message.setText(aText);

        Transport.send(message);
    }
}

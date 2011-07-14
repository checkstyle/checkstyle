////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple XML logger.
 * It outputs everything in UTF-8 (default XML encoding is UTF-8) in case
 * we want to localize error messages or simply that filenames are
 * localized and takes care about escaping as well.

 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 */
public class XMLLogger
    extends AutomaticBean
    implements AuditListener
{
    /** decimal radix */
    private static final int BASE_10 = 10;

    /** hex radix */
    private static final int BASE_16 = 16;

    /** close output stream in auditFinished */
    private boolean mCloseStream;

    /** helper writer that allows easy encoding and printing */
    private PrintWriter mWriter;

    /** some known entities to detect */
    private static final String[] ENTITIES = {"gt", "amp", "lt", "apos",
                                              "quot", };

    /**
     * Creates a new <code>XMLLogger</code> instance.
     * Sets the output to a defined stream.
     * @param aOS the stream to write logs to.
     * @param aCloseStream close aOS in auditFinished
     */
    public XMLLogger(OutputStream aOS, boolean aCloseStream)
    {
        setOutputStream(aOS);
        mCloseStream = aCloseStream;
    }

    /**
     * sets the OutputStream
     * @param aOS the OutputStream to use
     **/
    private void setOutputStream(OutputStream aOS)
    {
        try {
            final OutputStreamWriter osw = new OutputStreamWriter(aOS, "UTF-8");
            mWriter = new PrintWriter(osw);
        }
        catch (final UnsupportedEncodingException e) {
            // unlikely to happen...
            throw new ExceptionInInitializerError(e);
        }
    }

    /** {@inheritDoc} */
    public void auditStarted(AuditEvent aEvt)
    {
        mWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        final ResourceBundle compilationProperties =
            ResourceBundle.getBundle("checkstylecompilation");
        final String version =
            compilationProperties.getString("checkstyle.compile.version");

        mWriter.println("<checkstyle version=\"" + version + "\">");
    }

    /** {@inheritDoc} */
    public void auditFinished(AuditEvent aEvt)
    {
        mWriter.println("</checkstyle>");
        if (mCloseStream) {
            mWriter.close();
        }
        else {
            mWriter.flush();
        }
    }

    /** {@inheritDoc} */
    public void fileStarted(AuditEvent aEvt)
    {
        mWriter.println("<file name=\"" + encode(aEvt.getFileName()) + "\">");
    }

    /** {@inheritDoc} */
    public void fileFinished(AuditEvent aEvt)
    {
        mWriter.println("</file>");
    }

    /** {@inheritDoc} */
    public void addError(AuditEvent aEvt)
    {
        if (!SeverityLevel.IGNORE.equals(aEvt.getSeverityLevel())) {
            mWriter.print("<error" + " line=\"" + aEvt.getLine() + "\"");
            if (aEvt.getColumn() > 0) {
                mWriter.print(" column=\"" + aEvt.getColumn() + "\"");
            }
            mWriter.print(" severity=\""
                + aEvt.getSeverityLevel().getName()
                + "\"");
            mWriter.print(" message=\""
                + encode(aEvt.getMessage())
                + "\"");
            mWriter.println(" source=\""
                + encode(aEvt.getSourceName())
                + "\"/>");
        }
    }

    /** {@inheritDoc} */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        pw.println("<exception>");
        pw.println("<![CDATA[");
        aThrowable.printStackTrace(pw);
        pw.println("]]>");
        pw.println("</exception>");
        pw.flush();
        mWriter.println(encode(sw.toString()));
    }

    /**
     * Escape &lt;, &gt; &amp; &apos; and &quot; as their entities.
     * @param aValue the value to escape.
     * @return the escaped value if necessary.
     */
    public String encode(String aValue)
    {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < aValue.length(); i++) {
            final char c = aValue.charAt(i);
            switch (c) {
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            case '\"':
                sb.append("&quot;");
                break;
            case '&':
                final int nextSemi = aValue.indexOf(";", i);
                if ((nextSemi < 0)
                    || !isReference(aValue.substring(i, nextSemi + 1)))
                {
                    sb.append("&amp;");
                }
                else {
                    sb.append('&');
                }
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }

    /**
     * @return whether the given argument a character or entity reference
     * @param aEnt the possible entity to look for.
     */
    public boolean isReference(String aEnt)
    {
        if (!(aEnt.charAt(0) == '&') || !aEnt.endsWith(";")) {
            return false;
        }

        if (aEnt.charAt(1) == '#') {
            int prefixLength = 2; // "&#"
            int radix = BASE_10;
            if (aEnt.charAt(2) == 'x') {
                prefixLength++;
                radix = BASE_16;
            }
            try {
                Integer.parseInt(
                    aEnt.substring(prefixLength, aEnt.length() - 1), radix);
                return true;
            }
            catch (final NumberFormatException nfe) {
                return false;
            }
        }

        final String name = aEnt.substring(1, aEnt.length() - 1);
        for (String element : ENTITIES) {
            if (name.equals(element)) {
                return true;
            }
        }
        return false;
    }
}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;



import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;


/**
 * Simple XML logger.
 * It outputs everything in UTF8 (default XML encoding is UTF8) in case
 * we want to localize error messages or simply that filenames are
 * localized and takes care about escaping as well.

 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 */
public class XMLLogger
    implements AuditListener, Streamable
{
    /** the original wrapped stream */
    private OutputStream mStream;

    /** helper writer that allows easy encoding and printing */
    private PrintWriter mWriter;

    /** some known entities to detect */
    private final static String[] ENTITIES = {"gt", "amp", "lt", "apos",
                                              "quot"};

    /** Creates a new <code>XMLLogger</code> instance. */
    public XMLLogger()
    {
    }

    /**
     * sets the output to a defined stream
     * @param aOS the stream to write logs to.
     */
    public XMLLogger(OutputStream aOS)
    {
        setOutputStream(aOS);
    }

    /** @see Streamable **/
    public void setOutputStream(OutputStream aOS)
    {
        try {
            final OutputStreamWriter osw = new OutputStreamWriter(aOS, "UTF8");
            mWriter = new PrintWriter(osw);
            // keep a handle on the original stream
            // for getoutputstream
            mStream = aOS;
        }
        catch (UnsupportedEncodingException e) {
            // unlikely to happen...
            throw new ExceptionInInitializerError(e);
        }
    }

    /** @see Streamable **/
    public OutputStream getOutputStream()
    {
        return mStream;
    }

    /** @see AuditListener **/
    public void auditStarted(AuditEvent aEvt)
    {
        mWriter.println("<checkstyle>");
    }

    /** @see AuditListener **/
    public void auditFinished(AuditEvent aEvt)
    {
        mWriter.println("</checkstyle>");
        mWriter.flush();
    }

    /** @see AuditListener **/
    public void fileStarted(AuditEvent aEvt)
    {
        mWriter.println("<file name=\"" + aEvt.getFileName() + "\">");
    }

    /** @see AuditListener **/
    public void fileFinished(AuditEvent aEvt)
    {
        mWriter.println("</file>");
    }

    /** @see AuditListener **/
    public void addError(AuditEvent aEvt) {
        mWriter.println("<error " +
                        "line=\"" + aEvt.getLine() + "\" " +
                        "message=\"" + encode(aEvt.getMessage()) + "\"/>");
    }

    /** @see AuditListener **/
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
            char c = aValue.charAt(i);
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
                int nextSemi = aValue.indexOf(";", i);
                if ((nextSemi < 0) ||
                    !isReference(aValue.substring(i, nextSemi + 1)))
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
            if (aEnt.charAt(2) == 'x') {
                try {
                    Integer.parseInt(aEnt.substring(3, aEnt.length() - 1), 16);
                    return true;
                }
                catch (NumberFormatException nfe) {
                    return false;
                }
            }
            else {
                try {
                    Integer.parseInt(aEnt.substring(2, aEnt.length() - 1));
                    return true;
                }
                catch (NumberFormatException nfe) {
                    return false;
                }
            }
        }

        final String name = aEnt.substring(1, aEnt.length() - 1);
        for (int i = 0; i < ENTITIES.length; i++) {
            if (name.equals(ENTITIES[i])) {
                return true;
            }
        }
        return false;
    }
}

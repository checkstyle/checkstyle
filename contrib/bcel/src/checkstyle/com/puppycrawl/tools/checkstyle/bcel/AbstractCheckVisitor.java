//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import java.util.HashMap;
import org.apache.bcel.classfile.JavaClass;

/**
 * Abstract class for checks with visitors.
 * @author Rick Giles
 */
//TODO: Refactor with class Check
public abstract class AbstractCheckVisitor
    extends AbstractViolationReporter
    implements IObjectSetVisitor,
    IDeepVisitor
{
    /** the object for collecting messages. */
    private HashMap mMessageMap; // <String fileName, LocalizedMessages msg>
    /** Filename for when no file can be found */
    private String NO_FILE = "File not available";

    /** @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor */
    public org.apache.bcel.classfile.Visitor getClassFileVisitor()
    {
        return new EmptyClassFileVisitor();
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor
     */
    public org.apache.bcel.generic.Visitor getGenericVisitor()
    {
        return new EmptyGenericVisitor();
    }

     /**
     * Initialse the check. This is the time to verify that the check has
     * everything required to perform it job.
     */
    public void init()
    {
    }

    /**
     * Destroy the check. It is being retired from service.
     */
    public void destroy()
    {
    }

    /**
     * Set the global object used to collect messages.
     * @param aMessages the messages to log with
     */
    public final void setMessageMap(HashMap aMessageMap)
    {
        mMessageMap = aMessageMap;
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     * @param aArgs the details of the message
     *
     * @see java.text.MessageFormat
     */
    protected final void log(JavaClass javaClass, int aLine, String aKey, Object aArgs[])
    {
        // Should this be on the .java file instead of the .class file?
        final String file = javaClass.getFileName();
        log(file, aLine, aKey, aArgs);
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     * @param aArgs the details of the message
     *
     * @see java.text.MessageFormat
     */
    private final void log(String fileName, int aLine, String aKey, Object aArgs[])
    {
        LocalizedMessages localizedMessages = (LocalizedMessages) mMessageMap.get(fileName);
        if (localizedMessages == null) {
            localizedMessages = new LocalizedMessages();
            mMessageMap.put(fileName, localizedMessages);
        }
        localizedMessages.add(
            new LocalizedMessage(
                aLine,
                getMessageBundle(),
                aKey,
                aArgs,
                getSeverityLevel(),
                this.getClass()));
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     * @param aArgs the details of the message
     *
     * @see java.text.MessageFormat
     */
    protected final void log(int aLine, String aKey, Object aArgs[])
    {
        log(NO_FILE, aLine, aKey, aArgs);
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter
     */
    protected void log(int aLine, int aCol, String aKey, Object[] aArgs)
    {
          // Ignore the column, it is not relevant for .class files
          log(aLine, aKey, aArgs);
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void visitObject(Object aObject)
    {
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void leaveObject(Object aObject)
    {
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void visitSet(Set aJavaClassDefs)
    {
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void leaveSet(Set aJavaClassDefs)
    {
    }

    /**
     * Gets the deep BCEL visitor.
     * @return the deep BCEL visitor for this check.
     */
    public abstract IDeepVisitor getVisitor();
}

//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;

/**
 * Abstract class for checks with visitors.
 * @author Rick Giles
 */
//TODO: Refactor with class Check
public abstract class AbstractCheckVisitor
    extends AbstractViolationReporter
    implements IObjectSetVisitor
{
    /** the object for collecting messages. */
    private LocalizedMessages mMessages;

    /** @see com.puppycrawl.tools.checkstyle.bcel.IParserCheck */
    public org.apache.bcel.classfile.Visitor getClassFileVisitor()
    {
        return new EmptyClassFileVisitor();
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IParserCheck
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
    public final void setMessages(LocalizedMessages aMessages)
    {
        mMessages = aMessages;
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
        mMessages.add(
            new LocalizedMessage(
                aLine,
                getMessageBundle(),
                aKey,
                aArgs,
                getSeverityLevel(),
                this.getClass()));
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter
     */
    protected void log(int aLine, int aCol, String aKey, Object[] aArgs)
    {
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

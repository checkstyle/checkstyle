package com.mycompany.checks;

import java.io.File;
import com.puppycrawl.tools.checkstyle.api.*;

/**
 * An example for a user provided FileSetCheck,
 * checks that the number of files does not excced a certain limit.
 *
 * This Class is provided for educational purposes only, we do not
 * consider it useful to check your production code.
 *
 * @author lkuehne
 */
public class LimitImplementationFiles
    extends AbstractFileSetCheck
{
    /**
     * the maximium number of implementation files,
     * default is 100.
     */
    private int max = 100;

    /**
     * Give user a chance to configure max in the
     * config file.
     *
     * @param aMax the user specified maximum.
     */
    public void setMax(int aMax)
    {
        max = aMax;
    }

    /**
     * @see FileSetCheck
     */
    public void process(File[] files)
    {
        if (files != null && files.length > max) {

            // Build the error list. Here we fire only one error
            LocalizedMessage[] errors = new LocalizedMessage[1];

            // get the resource bundle to use for the message
            // will return "com.mycompany.checks.messages"
            final String bundle = getMessageBundle();

            // create the message arguments
            Object[] msgArgs = new Object[]{new Integer(max)};

            // create the actual message
            errors[0] = new LocalizedMessage(
                0, bundle, "max.files.exceeded", msgArgs);

            // fire the errors to the AuditListeners
            final String path = files[max].getPath();
            getMessageDispatcher().fireErrors(path, errors);
        }
    }
}

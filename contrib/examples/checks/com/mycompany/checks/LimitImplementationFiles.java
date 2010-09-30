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

            // figure out the file that contains the error
            final String path = files[max].getPath();

            // message collector is used to collect error messages,
            // needs to be reset before starting to collect error messages
            // for a file.
            getMessageCollector().reset();

            // message dispatcher is used to fire AuditEvents
            MessageDispatcher dispatcher = getMessageDispatcher();

            // signal start of file to AuditListeners
            dispatcher.fireFileStarted(path);

            // log the message
            log(0, "max.files.exceeded", new Integer(max));

            // you can call log() multiple times to flag multiple
            // errors in the same file

            // fire the errors for this file to the AuditListeners
            fireErrors(path);

            // signal end of file to AuditListeners
            dispatcher.fireFileFinished(path);
        }
    }
}

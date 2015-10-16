package com.google.checkstyle.test.base;

import java.io.OutputStream;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

/** A brief logger that only display info about errors. */
class BriefLogger extends DefaultLogger
{
    BriefLogger(OutputStream out)
    {
        super(out, true, out, false, false);
    }
    @Override
    public void auditStarted(AuditEvent event) {}
}

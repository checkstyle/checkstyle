package com.mycompany.filters;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.Utils;

public class FilesFilter
    extends AutomaticBean
    implements Filter
{
    private RE mFileRegexp;

    public FilesFilter()
        throws RESyntaxException
    {
        setFiles("^$");
    }
    
    public int decide(Object aObject)
    {
        if (!(aObject instanceof AuditEvent)) {
            return Filter.NEUTRAL;
        }
        
        final AuditEvent event = (AuditEvent) aObject;
        final String fileName = event.getFileName();
        if ((fileName != null) && mFileRegexp.match(fileName)) {
            return Filter.DENY;        
        }
        else {
            return Filter.NEUTRAL;
        }
    }

    public void setFiles(String aFilesPattern)
        throws RESyntaxException
    {
        mFileRegexp = Utils.getRE(aFilesPattern);
    }
}

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
    
    public boolean accept(AuditEvent aEvent)
    {
        final String fileName = aEvent.getFileName();
        return ((fileName == null) || !mFileRegexp.match(fileName));
    }

    public void setFiles(String aFilesPattern)
        throws RESyntaxException
    {
        mFileRegexp = Utils.getRE(aFilesPattern);
    }
}

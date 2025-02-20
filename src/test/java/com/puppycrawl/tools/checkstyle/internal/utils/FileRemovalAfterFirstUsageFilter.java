package com.puppycrawl.tools.checkstyle.internal.utils;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

import java.io.File;

public class FileRemovalAfterFirstUsageFilter implements Filter {
    boolean isUsed ;
    @Override
    public boolean accept(AuditEvent event) {
        if (!isUsed) {
            final File  file = new File(event.getFileName());
            isUsed =  file.delete();
            return isUsed;
        }
        return true;
    }
}

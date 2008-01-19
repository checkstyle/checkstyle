package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import org.junit.Test;

/** Tests SeverityMatchFilter */
public class SeverityMatchFilterTest
{
    private final SeverityMatchFilter filter = new SeverityMatchFilter();

    @Test
    public void testDefault()
    {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertFalse("no message", filter.accept(ev));
        SeverityLevel level = SeverityLevel.ERROR;
        LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null,
                level, null, this.getClass());
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", message);
        assertTrue("level:" + level, filter.accept(ev2));
        level = SeverityLevel.INFO;
        message = new LocalizedMessage(0, 0, "", "", null, level, null, this
                .getClass());
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", message);
        assertFalse("level:" + level, filter.accept(ev3));
    }

    @Test
    public void testSeverity()
    {
        filter.setSeverity("info");
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertTrue("no message", filter.accept(ev));
        SeverityLevel level = SeverityLevel.ERROR;
        LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null,
                level, null, this.getClass());
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", message);
        assertFalse("level:" + level, filter.accept(ev2));
        level = SeverityLevel.INFO;
        message = new LocalizedMessage(0, 0, "", "", null, level, null, this
                .getClass());
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", message);
        assertTrue("level:" + level, filter.accept(ev3));
    }

    @Test
    public void testAcceptOnMatch()
    {
        filter.setSeverity("info");
        filter.setAcceptOnMatch(false);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertFalse("no message", filter.accept(ev));
        SeverityLevel level = SeverityLevel.ERROR;
        LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null,
                level, null, this.getClass());
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", message);
        assertTrue("level:" + level, filter.accept(ev2));
        level = SeverityLevel.INFO;
        message = new LocalizedMessage(0, 0, "", "", null, level, null, this
                .getClass());
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", message);
        assertFalse("level:" + level, filter.accept(ev3));
    }
}

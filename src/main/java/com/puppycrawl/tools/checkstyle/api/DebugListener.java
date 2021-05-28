////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public class DebugListener extends AutomaticBean implements AuditListener {

    private final Map<String, AtomicLong> fileTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileMax = new HashMap<String, AtomicLong>();

    private final Map<String, AtomicLong> filterTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> filterUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> filterMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> filterMax = new HashMap<String, AtomicLong>();
    private final Stack<Long> filterStartTimeMemory = new Stack<Long>();
    private final Stack<String> filterNamesMemory = new Stack<String>();

    private final Map<String, AtomicLong> treeWalkerFilterTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> treeWalkerFilterUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> treeWalkerFilterMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> treeWalkerFilterMax = new HashMap<String, AtomicLong>();
    private final Stack<Long> treeWalkerFilterStartTimeMemory = new Stack<Long>();
    private final Stack<String> treeWalkerFilterNamesMemory = new Stack<String>();

    private final Map<String, AtomicLong> beforeExecutionFileFilterTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> beforeExecutionFileFilterUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> beforeExecutionFileFilterMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> beforeExecutionFileFilterMax = new HashMap<String, AtomicLong>();
    private final Stack<Long> beforeExecutionFileFilterStartTimeMemory = new Stack<Long>();
    private final Stack<String> beforeExecutionFileFilterNamesMemory = new Stack<String>();

    private final Map<String, AtomicLong> fileSetTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileSetUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileSetMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> fileSetMax = new HashMap<String, AtomicLong>();

    private final Map<String, AtomicLong> checkTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> checkUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> checkMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> checkMax = new HashMap<String, AtomicLong>();

    private final Map<String, AtomicLong> parseTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> parseUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> parseMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> parseMax = new HashMap<String, AtomicLong>();

    private final Map<String, AtomicLong> customTime = new TreeMap<String, AtomicLong>();
    private final Map<String, AtomicLong> customUses = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> customMin = new HashMap<String, AtomicLong>();
    private final Map<String, AtomicLong> customMax = new HashMap<String, AtomicLong>();
    private final Stack<Long> customStartTimeMemory = new Stack<Long>();
    private final Stack<String> customNamesMemory = new Stack<String>();

    private long startTime;
    private long fileStartTime;
    private long filterStartTime;
    private long treeWalkerFilterStartTime;
    private long beforeExecutionFileFilterStartTime;
    private long fileSetStartTime;
    private long checkStartTime;
    private long parseStartTime;
    private long javaDocParseStartTime;
    private long customStartTime;

    @Override
    public void auditStarted(AuditEvent event) {
        this.startTime = System.nanoTime();
        this.fileStartTime = 0;
        this.filterStartTime = 0;
        this.treeWalkerFilterStartTime = 0;
        this.beforeExecutionFileFilterStartTime = 0;
        this.fileSetStartTime = 0;
        this.checkStartTime = 0;
        this.customStartTime = 0;

        this.fileTime.clear();
        this.fileUses.clear();
        this.fileMin.clear();
        this.fileMax.clear();
        this.filterTime.clear();
        this.filterUses.clear();
        this.filterMin.clear();
        this.filterMax.clear();
        this.filterStartTimeMemory.clear();
        this.filterNamesMemory.clear();
        this.treeWalkerFilterTime.clear();
        this.treeWalkerFilterUses.clear();
        this.treeWalkerFilterMin.clear();
        this.treeWalkerFilterMax.clear();
        this.treeWalkerFilterStartTimeMemory.clear();
        this.treeWalkerFilterNamesMemory.clear();
        this.beforeExecutionFileFilterTime.clear();
        this.beforeExecutionFileFilterUses.clear();
        this.beforeExecutionFileFilterMin.clear();
        this.beforeExecutionFileFilterMax.clear();
        this.beforeExecutionFileFilterStartTimeMemory.clear();
        this.beforeExecutionFileFilterNamesMemory.clear();
        this.fileSetTime.clear();
        this.fileSetUses.clear();
        this.fileSetMin.clear();
        this.fileSetMax.clear();
        this.checkTime.clear();
        this.checkUses.clear();
        this.checkMin.clear();
        this.checkMax.clear();
        this.parseTime.clear();
        this.parseUses.clear();
        this.parseMin.clear();
        this.parseMax.clear();
        this.customTime.clear();
        this.customUses.clear();
        this.customMin.clear();
        this.customMax.clear();
        this.customStartTimeMemory.clear();
        this.customNamesMemory.clear();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        if (!filterStartTimeMemory.isEmpty()) {
            System.err.println("Error: Filter has left over times! " + filterNamesMemory);
        }
        if (!treeWalkerFilterStartTimeMemory.isEmpty()) {
            System.err
                    .println("Error: TreeWalker Filter has left over times! " + filterNamesMemory);
        }
        if (!beforeExecutionFileFilterStartTimeMemory.isEmpty()) {
            System.err.println("Error: BeforeExecutionFile Filter has left over times! "
                    + beforeExecutionFileFilterNamesMemory.toString());
        }
        if (!customStartTimeMemory.isEmpty()) {
            System.err
                    .println("Error: custom has left over times! " + customNamesMemory.toString());
        }

        System.out.println("------------------");
        System.out.println("Run Time: " + format(System.nanoTime() - this.startTime, 1));
        System.out.println();

        System.out.println("Files: (" + fileTime.size() + ")");

        for (String key : fileTime.keySet()) {
            System.out.println(key + "\t" + fileUses.get(key).get() + "\t"
                    + format(fileTime.get(key).get(), 1) + "\t" //
                    + format(fileMin.get(key).get(), 1) + "\t" //
                    + format(fileMax.get(key).get(), 1) + "\t"
                    + format(fileTime.get(key).get(), fileUses.get(key).get()));
        }

        System.out.println();
        System.out.println("Filters: (" + filterTime.size() + ")");

        for (String key : filterTime.keySet()) {
            System.out.println(key + "\t" + filterUses.get(key).get() + "\t"
                    + format(filterTime.get(key).get(), 1) + "\t" //
                    + format(filterMin.get(key).get(), 1) + "\t" //
                    + format(filterMax.get(key).get(), 1) + "\t"
                    + format(filterTime.get(key).get(), filterUses.get(key).get()));
        }

        System.out.println();
        System.out.println("TreeWalker Filters: (" + treeWalkerFilterTime.size() + ")");

        for (String key : treeWalkerFilterTime.keySet()) {
            System.out.println(key + "\t" + treeWalkerFilterUses.get(key).get() + "\t"
                    + format(treeWalkerFilterTime.get(key).get(), 1) + "\t" //
                    + format(treeWalkerFilterMin.get(key).get(), 1) + "\t" //
                    + format(treeWalkerFilterMax.get(key).get(), 1) + "\t"
                    + format(treeWalkerFilterTime.get(key).get(),
                    treeWalkerFilterUses.get(key).get()));
        }

        System.out.println();
        System.out.println(
                "Before Execution File Filters: (" + beforeExecutionFileFilterTime.size() + ")");

        for (String key : beforeExecutionFileFilterTime.keySet()) {
            System.out.println(key + "\t" + beforeExecutionFileFilterUses.get(key).get() + "\t"
                    + format(beforeExecutionFileFilterTime.get(key).get(), 1) + "\t" //
                    + format(beforeExecutionFileFilterMin.get(key).get(), 1) + "\t" //
                    + format(beforeExecutionFileFilterMax.get(key).get(), 1) + "\t"
                    + format(beforeExecutionFileFilterTime.get(key).get(),
                    beforeExecutionFileFilterUses.get(key).get()));
        }

        System.out.println();
        System.out.println("FileSets: (" + fileSetTime.size() + ")");

        for (String key : fileSetTime.keySet()) {
            System.out.println(key + "\t" + fileSetUses.get(key).get() + "\t"
                    + format(fileSetTime.get(key).get(), 1) + "\t" //
                    + format(fileSetMin.get(key).get(), 1) + "\t" //
                    + format(fileSetMax.get(key).get(), 1) + "\t"
                    + format(fileSetTime.get(key).get(), fileSetUses.get(key).get()));
        }

        System.out.println();
        System.out.println("Checks: (" + checkTime.size() + ")");

        for (String key : checkTime.keySet()) {
            System.out.println(key + "\t" + checkUses.get(key).get() + "\t"
                    + format(checkTime.get(key).get(), 1) + "\t" //
                    + format(checkMin.get(key).get(), 1) + "\t" //
                    + format(checkMax.get(key).get(), 1) + "\t"
                    + format(checkTime.get(key).get(), checkUses.get(key).get()));
        }

        System.out.println();
        System.out.println("Parses: (" + parseTime.size() + ")");

        for (String key : parseTime.keySet()) {
            System.out.println(key + "\t" + parseUses.get(key).get() + "\t"
                    + format(parseTime.get(key).get(), 1) + "\t" //
                    + format(parseMin.get(key).get(), 1) + "\t" //
                    + format(parseMax.get(key).get(), 1) + "\t"
                    + format(parseTime.get(key).get(), parseUses.get(key).get()));
        }

        System.out.println();
        System.out.println();
        System.out.println("Customs: (" + customTime.size() + ")");

        for (String key : customTime.keySet()) {
            System.out.println(key + "\t" + customUses.get(key).get() + "\t"
                    + format(customTime.get(key).get(), 1) + "\t" //
                    + format(customMin.get(key).get(), 1) + "\t" //
                    + format(customMax.get(key).get(), 1) + "\t"
                    + format(customTime.get(key).get(), customUses.get(key).get()));
        }

        System.out.println("------------------");
    }

    private static String format(long i, long j) {
        double d;

        if (j == 1)
            d = i;
        else
            d = (double) i / j;

        double temp = d / 1000000000;

        return String.valueOf((long) (temp * 1000) / 1000d);
    }

    @Override
    public void fileStarted(AuditEvent event) {
        final String src = event.getFileName();
        System.out.println("File started: " + src);

        this.fileStartTime = System.nanoTime();
    }

    @Override
    public void filterStarted(AuditEvent event) {
        if (this.filterStartTime != 0)
            filterStartTimeMemory.push(this.filterStartTime);
        this.filterNamesMemory.push(event.getSource().getClass().getSimpleName());

        this.filterStartTime = System.nanoTime();
    }

    @Override
    public void treeWalkerFilterStarted(AuditEvent event) {
        if (this.treeWalkerFilterStartTime != 0)
            treeWalkerFilterStartTimeMemory.push(this.treeWalkerFilterStartTime);
        this.treeWalkerFilterNamesMemory.push(event.getSource().getClass().getSimpleName());

        this.treeWalkerFilterStartTime = System.nanoTime();
    }

    @Override
    public void beforeExecutionFileFilterStarted(AuditEvent event) {
        if (this.beforeExecutionFileFilterStartTime != 0)
            beforeExecutionFileFilterStartTimeMemory.push(this.beforeExecutionFileFilterStartTime);
        this.beforeExecutionFileFilterNamesMemory
                .push(event.getSource().getClass().getSimpleName());

        this.beforeExecutionFileFilterStartTime = System.nanoTime();
    }

    @Override
    public void fileSetStarted(AuditEvent event) {
        this.fileSetStartTime = System.nanoTime();
    }

    @Override
    public void checkStarted(AuditEvent event) {
        this.checkStartTime = System.nanoTime();
    }

    @Override
    public void parseStarted(AuditEvent event) {
        this.parseStartTime = System.nanoTime();
    }

    @Override
    public void JavaDocParseStarted(AuditEvent event) {
        this.javaDocParseStartTime = System.nanoTime();
    }

    @Override
    public void CustomStarted(AuditEvent event) {
        if (this.customStartTime != 0)
            customStartTimeMemory.push(this.customStartTime);
        this.customNamesMemory.push(event.getSource().toString());

        this.customStartTime = System.nanoTime();
    }

    @Override
    public void parseFinished(AuditEvent event) {
        if (this.parseStartTime == 0) {
            System.err.println("Error: Parse has no start time!");
            return;
        }

        final String src = "Java";
        final long d = System.nanoTime() - this.parseStartTime;
        this.parseStartTime = 0;

        if (d < 0) {
            System.err.println("Error: Parse has a negative time!");
            return;
        }

        if (this.parseTime.get(src) == null) {
            this.parseTime.put(src, new AtomicLong());
            this.parseUses.put(src, new AtomicLong());
            this.parseMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.parseMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.parseMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.parseMax.get(src);
        if (d > max.get())
            max.set(d);

        this.parseTime.get(src).addAndGet(d);
        this.parseUses.get(src).addAndGet(1);

        // reduce time from fileset as this is part of its process
        if (this.fileSetStartTime != 0) {
            this.fileSetStartTime += d;
        }
    }

    @Override
    public void JavaDocParseFinished(AuditEvent event) {
        if (this.javaDocParseStartTime == 0) {
            System.err.println("Error: JavaDoc Parse has no start time!");
            return;
        }

        final String src = "JavaDoc";
        final long d = System.nanoTime() - this.javaDocParseStartTime;
        this.javaDocParseStartTime = 0;

        if (d < 0) {
            System.err.println("Error: JavaDoc Parse has a negative time!");
            return;
        }

        if (this.parseTime.get(src) == null) {
            this.parseTime.put(src, new AtomicLong());
            this.parseUses.put(src, new AtomicLong());
            this.parseMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.parseMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.parseMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.parseMax.get(src);
        if (d > max.get())
            max.set(d);

        this.parseTime.get(src).addAndGet(d);
        this.parseUses.get(src).addAndGet(1);

        // reduce time from check as this is part of its process
        if (this.checkStartTime != 0) {
            this.checkStartTime += d;
        }
        if (this.fileSetStartTime != 0) {
            this.fileSetStartTime += d;
        }
    }

    @Override
    public void checkFinished(AuditEvent event) {
        if (this.checkStartTime == 0) {
            System.err.println("Error: Check has no start time!");
            return;
        }

        final String src = event.getSource().getClass().getSimpleName();
        final long d = System.nanoTime() - this.checkStartTime;
        this.checkStartTime = 0;

        if (d < 0) {
            System.err.println("Error: Check has a negative time!");
            return;
        }

        if (this.checkTime.get(src) == null) {
            this.checkTime.put(src, new AtomicLong());
            this.checkUses.put(src, new AtomicLong());
            this.checkMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.checkMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.checkMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.checkMax.get(src);
        if (d > max.get())
            max.set(d);

        this.checkTime.get(src).addAndGet(d);
        this.checkUses.get(src).addAndGet(1);

        // reduce time from fileSet as this is part of its process
        if (this.fileSetStartTime != 0) {
            this.fileSetStartTime += d;
        }
    }

    @Override
    public void fileSetFinished(AuditEvent event) {
        if (this.fileSetStartTime == 0) {
            System.err.println("Error: FileSet has no start time!");
            return;
        }

        final String src = event.getSource().getClass().getSimpleName();
        final long d = System.nanoTime() - this.fileSetStartTime;
        this.fileSetStartTime = 0;

        if (d < 0) {
            System.err.println("Error: FileSet has a negative time!");
            return;
        }

        if (this.fileSetTime.get(src) == null) {
            this.fileSetTime.put(src, new AtomicLong());
            this.fileSetUses.put(src, new AtomicLong());
            this.fileSetMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.fileSetMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.fileSetMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.fileSetMax.get(src);
        if (d > max.get())
            max.set(d);

        this.fileSetTime.get(src).addAndGet(d);
        this.fileSetUses.get(src).addAndGet(1);
    }

    @Override
    public void beforeExecutionFileFilterFinished(AuditEvent event) {
        if (this.beforeExecutionFileFilterStartTime == 0) {
            System.err.println("Error: BeforeExecutionFileFilter has no start time!");
            return;
        }

        final String src = event.getSource().getClass().getSimpleName();
        final String startSrc = this.beforeExecutionFileFilterNamesMemory.pop();

        if (!startSrc.equals(src)) {
            System.err.println(
                    "Error: BeforeExecutionFileFilter name mis-match: " + src + " vs " + startSrc);
        }

        final long d = System.nanoTime() - this.beforeExecutionFileFilterStartTime;

        if (this.beforeExecutionFileFilterStartTimeMemory.size() > 0) {
            this.beforeExecutionFileFilterStartTime = this.beforeExecutionFileFilterStartTimeMemory
                    .pop() + d;
        }
        else {
            this.beforeExecutionFileFilterStartTime = 0;
        }

        if (d < 0) {
            System.err.println("Error: BeforeExecutionFileFilter has a negative time!");
            return;
        }

        if (this.beforeExecutionFileFilterTime.get(src) == null) {
            this.beforeExecutionFileFilterTime.put(src, new AtomicLong());
            this.beforeExecutionFileFilterUses.put(src, new AtomicLong());
            this.beforeExecutionFileFilterMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.beforeExecutionFileFilterMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.beforeExecutionFileFilterMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.beforeExecutionFileFilterMax.get(src);
        if (d > max.get())
            max.set(d);

        this.beforeExecutionFileFilterTime.get(src).addAndGet(d);
        this.beforeExecutionFileFilterUses.get(src).addAndGet(1);
    }

    @Override
    public void filterFinished(AuditEvent event) {
        if (this.filterStartTime == 0) {
            System.err.println("Error: Filter has no start time!");
            return;
        }

        final String src = event.getSource().getClass().getSimpleName();
        final String startSrc = this.filterNamesMemory.pop();

        if (!startSrc.equals(src)) {
            System.err.println("Error: Filter name mis-match: " + src + " vs " + startSrc);
        }

        final long d = System.nanoTime() - this.filterStartTime;

        if (this.filterStartTimeMemory.size() > 0)
            this.filterStartTime = this.filterStartTimeMemory.pop() + d;
        else
            this.filterStartTime = 0;

        if (d < 0) {
            System.err.println("Error: Filter has a negative time!");
            return;
        }

        if (this.filterTime.get(src) == null) {
            this.filterTime.put(src, new AtomicLong());
            this.filterUses.put(src, new AtomicLong());
            this.filterMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.filterMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.filterMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.filterMax.get(src);
        if (d > max.get())
            max.set(d);

        this.filterTime.get(src).addAndGet(d);
        this.filterUses.get(src).addAndGet(1);
    }

    @Override
    public void treeWalkerFilterFinished(AuditEvent event) {
        if (this.treeWalkerFilterStartTime == 0) {
            System.err.println("Error: TreeWalker Filter has no start time!");
            return;
        }

        final String src = event.getSource().getClass().getSimpleName();
        final String startSrc = this.treeWalkerFilterNamesMemory.pop();

        if (!startSrc.equals(src)) {
            System.err
                    .println("Error: TreeWalker Filter name mis-match: " + src + " vs " + startSrc);
        }

        final long d = System.nanoTime() - this.treeWalkerFilterStartTime;

        if (this.treeWalkerFilterStartTimeMemory.size() > 0)
            this.treeWalkerFilterStartTime = this.treeWalkerFilterStartTimeMemory.pop() + d;
        else
            this.treeWalkerFilterStartTime = 0;

        if (d < 0) {
            System.err.println("Error: TreeWalker Filter has a negative time!");
            return;
        }

        if (this.treeWalkerFilterTime.get(src) == null) {
            this.treeWalkerFilterTime.put(src, new AtomicLong());
            this.treeWalkerFilterUses.put(src, new AtomicLong());
            this.treeWalkerFilterMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.treeWalkerFilterMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.treeWalkerFilterMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.treeWalkerFilterMax.get(src);
        if (d > max.get())
            max.set(d);

        this.treeWalkerFilterTime.get(src).addAndGet(d);
        this.treeWalkerFilterUses.get(src).addAndGet(1);

        // reduce time from fileSet as this is part of its process
        if (this.fileSetStartTime != 0) {
            this.fileSetStartTime += d;
        }
    }

    @Override
    public void fileFinished(AuditEvent event) {
        if (this.fileStartTime == 0) {
            System.err.println("Error: File has no start time!");
            return;
        }

        final String src = event.getFileName();
        final long d = System.nanoTime() - this.fileStartTime;
        this.fileStartTime = 0;

        if (d < 0) {
            System.err.println("Error: File has a negative time!");
            return;
        }

        if (this.fileTime.get(src) == null) {
            this.fileTime.put(src, new AtomicLong());
            this.fileUses.put(src, new AtomicLong());
            this.fileMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.fileMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.fileMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.fileMax.get(src);
        if (d > max.get())
            max.set(d);

        this.fileTime.get(src).addAndGet(d);
        this.fileUses.get(src).addAndGet(1);
    }

    @Override
    public void CustomFinished(AuditEvent event) {
        if (this.customStartTime == 0)
            return;

        final String src = event.getSource().toString();
        final String startSrc = this.customNamesMemory.pop();

        if (!startSrc.equals(src)) {
            System.err.println("Custom name mis-match: " + src + " vs " + startSrc);
        }

        final long d = System.nanoTime() - this.customStartTime;

        if (this.customStartTimeMemory.size() > 0)
            this.customStartTime = this.customStartTimeMemory.pop() + d;
        else
            this.customStartTime = 0;

        if (this.customTime.get(src) == null) {
            this.customTime.put(src, new AtomicLong());
            this.customUses.put(src, new AtomicLong());
            this.customMin.put(src, new AtomicLong(Long.MAX_VALUE));
            this.customMax.put(src, new AtomicLong(Long.MIN_VALUE));
        }

        final AtomicLong min = this.customMin.get(src);
        if (d < min.get())
            min.set(d);

        final AtomicLong max = this.customMax.get(src);
        if (d > max.get())
            max.set(d);

        this.customTime.get(src).addAndGet(d);
        this.customUses.get(src).addAndGet(1);
    }

    @Override
    public void addError(AuditEvent event) {
        // nothing
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        // nothing
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        // nothing
    }

}
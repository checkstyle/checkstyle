////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.Patch;
import com.puppycrawl.tools.checkstyle.api.*;

/**
 * <p>
 * Filter {@code SuppressionFilter} rejects audit events for Check violations according to a
 * patch file.
 * </p>
 *
 * @since 8.34
 */
public class SuppressionPatchFilter extends AutomaticBean implements Filter, ExternalResourceHolder {

    /**
     * Specify the location of the patch file.
     */
    private String file;

    /**
     * Control what to do when the file is not existing. If {@code optional} is
     * set to {@code false} the file must exist, or else it ends with error.
     * On the other hand if optional is {@code true} and file is not found,
     * the filter accept all audit events.
     */
    private boolean optional;

    /**
     * Set of individual suppresses.
     */
    private PatchFilterSet filters = new PatchFilterSet();

    /**
     * Setter to specify the location of the patch file.
     *
     * @param fileName name of the patch file.
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Setter to control what to do when the file is not existing.
     * If {@code optional} is set to {@code false} the file must exist, or else
     * it ends with error. On the other hand if optional is {@code true}
     * and file is not found, the filter accept all audit events.
     *
     * @param optional tells if config file existence is optional.
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean accept(AuditEvent event) {
        return filters.accept(event);
    }

    /**
     * To finish the part of this component's setup.
     *
     * @throws Exception if there is a configuration error.
     */
    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        if (file != null) {
            loadPatchFile(file);
        }
    }

    private PatchFilterSet loadPatchFile(String patchFileName) {
        try {
            final List<String> originPatch = Files.readAllLines(new File(patchFileName).toPath());
            final List<List<String>> patchList = getPatchList(originPatch);
            for (List<String> singlePatch : patchList) {
                final String fileName = getFileName(singlePatch);
                final Patch<String> patch = UnifiedDiffUtils.parseUnifiedDiff(singlePatch);
                final List<List<Integer>> lineRangeList = getLineRange(patch);
                final SuppressionPatchFilterElement element =
                        new SuppressionPatchFilterElement(fileName, lineRangeList);
                filters.addFilter(element);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filters;
    }

    /**
     * To seperate different files's diff contents when there are multiple files in patch file.
     * @param originPatch List of String
     * @return patchedList List of List of String
     */
    private List<List<String>> getPatchList(List<String> originPatch) {
        final List<List<String>> patchedList = new ArrayList<>();
        boolean flag = true;
        List<String> singlePatched = new ArrayList<>();
        for (String str : originPatch) {
            if (str.startsWith("diff ")) {
                if (flag) {
                    flag = false;
                }
                else {
                    patchedList.add(singlePatched);
                    singlePatched = new ArrayList<>();
                }
            }
            singlePatched.add(str);
        }
        patchedList.add(singlePatched);
        return patchedList;
    }

    private String getFileName(List<String> singlePatch) {
        String fileName = null;
        for (String str : singlePatch) {
            if (str.startsWith("+++ ")) {
                fileName = str.split("\\s")[1];
                final String fileSplit = "/";
                if (fileName.contains(fileSplit)) {
                    final String[] fileNameArray = fileName.split(fileSplit);
                    fileName = fileNameArray[fileNameArray.length - 1];
                }
                break;
            }
        }
        return fileName;
    }

    private List<List<Integer>> getLineRange(Patch<String> patch) {
        final List<List<Integer>> lineRangeList = new ArrayList<>();
        for (int i = 0; i < patch.getDeltas().size(); i++) {
            final Chunk targetChunk = patch.getDeltas().get(i).getTarget();
            final List<Integer> lineRange = new ArrayList<>();
            lineRange.add(targetChunk.getPosition());
            lineRange.add(targetChunk.getPosition() + targetChunk.getLines().size());
            lineRangeList.add(lineRange);
        }
        return lineRangeList;
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Collections.singleton(file);
    }

}

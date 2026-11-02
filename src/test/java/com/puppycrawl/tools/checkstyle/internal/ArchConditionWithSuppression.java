///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import java.util.HashSet;
import java.util.Set;

import com.tngtech.archunit.core.domain.properties.HasName;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;

/**
 * ArchCondition that ignores violations of another condition for types or members in the
 * suppression list. A suppression is used only if its item violates the condition.
 *
 * @param <T> type of the checked item
 */
public final class ArchConditionWithSuppression<T extends HasName.AndFullName>
    extends ArchCondition<T> {

    private final ArchCondition<T> condition;
    private final Set<String> suppressions;
    private final Set<String> usedSuppressions;

    /**
     * Creates a condition that tolerates violations of the given condition for suppressed items.
     *
     * @param condition the condition to check
     * @param suppressions full names of items allowed to violate the condition
     */
    public ArchConditionWithSuppression(ArchCondition<T> condition, Set<String> suppressions) {
        super(condition.getDescription() + " or be suppressed");
        this.condition = condition;
        this.suppressions = suppressions;
        usedSuppressions = new HashSet<>();
    }

    /**
     * Returns the suppression list.
     *
     * @return the suppression list
     */
    public Set<String> getSuppressions() {
        return suppressions;
    }

    /**
     * Returns the suppressions that were actually needed (their item violated the condition).
     *
     * @return the used suppressions
     */
    public Set<String> getUsedSuppressions() {
        return usedSuppressions;
    }

    @Override
    public void check(T item, ConditionEvents events) {
        final ConditionEvents conditionEvents = ConditionEvents.Factory.create();
        condition.check(item, conditionEvents);
        if (conditionEvents.containViolation()) {
            final String fullName = item.getFullName();
            if (suppressions.contains(fullName)) {
                usedSuppressions.add(fullName);
            }
            else {
                conditionEvents.getViolating().forEach(events::add);
            }
        }
    }

}

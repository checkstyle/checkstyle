////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * Validate commit message has proper structure.
 *
 * Commits to check are resolved from different places according
 * to type of commit in current HEAD. If current HEAD commit is
 * non-merge commit , previous commits are resolved due to current
 * HEAD commit. Otherwise if it is a merge commit, it will invoke
 * resolving previous commits due to commits which was merged.
 *
 * After calculating commits to start with ts resolves previous
 * commits according to COMMITS_RESOLUTION_MODE variable.
 * At default(BY_LAST_COMMIT_AUTHOR) it checks first commit author
 * and return all consecutive commits with same author. Second
 * mode(BY_COUNTER) makes returning first PREVIOUS_COMMITS_TO_CHECK_COUNT
 * commits after starter commit.
 *
 * Resolved commits are filtered according to author. If commit author
 * belong to list USERS_EXCLUDED_FROM_VALIDATION then this commit will
 * not be validated.
 *
 * Filtered commit list is checked if their messages has proper structure.
 *
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class CommitValidationTest {

    private static final List<String> USERS_EXCLUDED_FROM_VALIDATION =
            Collections.singletonList("Roman Ivanov");

    private static final String ISSUE_COMMIT_MESSAGE_REGEX_PATTERN = "^Issue #\\d*: .*[^\\.]$";
    private static final String PR_COMMIT_MESSAGE_REGEX_PATTERN = "^Pull #\\d*: .*[^\\.]$";
    private static final String OTHER_COMMIT_MESSAGE_REGEX_PATTERN =
            "^(minor|config|infra|doc|spelling): .*[^\\.]$";

    private static final String ACCEPTED_COMMIT_MESSAGE_REGEX_PATTERN =
              "(" + ISSUE_COMMIT_MESSAGE_REGEX_PATTERN + ")|"
              + "(" + PR_COMMIT_MESSAGE_REGEX_PATTERN + ")|"
              + "(" + OTHER_COMMIT_MESSAGE_REGEX_PATTERN + ")";

    private static final Pattern ACCEPTED_COMMIT_MESSAGE_PATTERN =
            Pattern.compile(ACCEPTED_COMMIT_MESSAGE_REGEX_PATTERN, Pattern.DOTALL);

    private static final String NEWLINE_REGEX_PATTERN = "\r\n?|\n";

    private static final Pattern NEWLINE_PATTERN = Pattern.compile(NEWLINE_REGEX_PATTERN);

    private static final int PREVIOUS_COMMITS_TO_CHECK_COUNT = 10;

    private enum CommitsResolutionMode {
        BY_COUNTER, BY_LAST_COMMIT_AUTHOR
    }

    private static final CommitsResolutionMode COMMITS_RESOLUTION_MODE =
            CommitsResolutionMode.BY_LAST_COMMIT_AUTHOR;

    private static List<RevCommit> lastCommits;

    @BeforeClass
    public static void setUp() throws Exception {
        lastCommits = getCommitsToCheck();
    }

    @Test
    public void testCommitMessageHasProperStructure() {
        for (RevCommit commit : filterValidCommits(lastCommits)) {
            final String commitId = commit.getId().getName();
            final String commitMessage = commit.getFullMessage();
            final Matcher matcher = ACCEPTED_COMMIT_MESSAGE_PATTERN.matcher(commitMessage);
            assertTrue(getInvalidCommitMessageFormattingError(commitId, commitMessage),
                    matcher.matches());
        }
    }

    @Test
    public void testCommitMessageHasSingleLine() {
        for (RevCommit commit : filterValidCommits(lastCommits)) {
            final String commitId = commit.getId().getName();
            final String commitMessage = commit.getFullMessage();
            final Matcher matcher = NEWLINE_PATTERN.matcher(commitMessage);
            if (matcher.find()) {
                /**
                 * Git by default put newline character at the end of commit message. To check
                 * if commit message has a single line we have to make sure that the only
                 * newline character found is in the end of commit message.
                 */
                final boolean isFoundNewLineCharacterAtTheEndOfMessage =
                        matcher.end() == commitMessage.length();
                assertTrue(getInvalidCommitMessageFormattingError(commitId, commitMessage),
                        isFoundNewLineCharacterAtTheEndOfMessage);
            }
        }
    }

    private static List<RevCommit> getCommitsToCheck() throws Exception {
        final List<RevCommit> commits;
        try (Repository repo = new FileRepositoryBuilder().findGitDir().build()) {
            final RevCommitsPair revCommitsPair = resolveRevCommitsPair(repo);
            if (COMMITS_RESOLUTION_MODE == CommitsResolutionMode.BY_COUNTER) {
                commits = getCommitsByCounter(revCommitsPair.getFirst());
                commits.addAll(getCommitsByCounter(revCommitsPair.getSecond()));
            }
            else {
                commits = getCommitsByLastCommitAuthor(revCommitsPair.getFirst());
                commits.addAll(getCommitsByLastCommitAuthor(revCommitsPair.getSecond()));
            }
        }
        return commits;
    }

    private static List<RevCommit> filterValidCommits(List<RevCommit> revCommits) {
        final List<RevCommit> filteredCommits = new LinkedList<>();
        for (RevCommit commit : revCommits) {
            final String commitAuthor = commit.getAuthorIdent().getName();
            if (!USERS_EXCLUDED_FROM_VALIDATION.contains(commitAuthor)) {
                filteredCommits.add(commit);
            }
        }
        return filteredCommits;
    }

    private static RevCommitsPair resolveRevCommitsPair(Repository repo) {
        RevCommitsPair revCommitIteratorPair;

        try (RevWalk revWalk = new RevWalk(repo)) {
            final Iterator<RevCommit> first;
            final Iterator<RevCommit> second;
            final ObjectId headId = repo.resolve(Constants.HEAD);
            final RevCommit headCommit = revWalk.parseCommit(headId);

            if (isMergeCommit(headCommit)) {
                final RevCommit firstParent = headCommit.getParent(0);
                final RevCommit secondParent = headCommit.getParent(1);

                try (Git git = new Git(repo)) {
                    first = git.log().add(firstParent).call().iterator();
                    second = git.log().add(secondParent).call().iterator();
                }
            }
            else {
                try (Git git = new Git(repo)) {
                    first = git.log().call().iterator();
                }
                second = Collections.emptyIterator();
            }

            revCommitIteratorPair =
                    new RevCommitsPair(new OmitMergeCommitsIterator(first),
                            new OmitMergeCommitsIterator(second));
        }
        catch (GitAPIException | IOException ex) {
            revCommitIteratorPair = new RevCommitsPair();
        }

        return revCommitIteratorPair;
    }

    private static boolean isMergeCommit(RevCommit currentCommit) {
        return currentCommit.getParentCount() > 1;
    }

    private static List<RevCommit> getCommitsByCounter(
            Iterator<RevCommit> previousCommitsIterator) {
        return Lists.newArrayList(Iterators.limit(previousCommitsIterator,
                PREVIOUS_COMMITS_TO_CHECK_COUNT));
    }

    private static List<RevCommit> getCommitsByLastCommitAuthor(
            Iterator<RevCommit> previousCommitsIterator) {
        final List<RevCommit> commits = new LinkedList<>();

        if (previousCommitsIterator.hasNext()) {
            final RevCommit lastCommit = previousCommitsIterator.next();
            final String lastCommitAuthor = lastCommit.getAuthorIdent().getName();
            commits.add(lastCommit);

            boolean wasLastCheckedCommitAuthorSameAsLastCommit = true;
            while (previousCommitsIterator.hasNext()
                    && wasLastCheckedCommitAuthorSameAsLastCommit) {
                final RevCommit currentCommit = previousCommitsIterator.next();
                final String currentCommitAuthor = currentCommit.getAuthorIdent().getName();
                if (currentCommitAuthor.equals(lastCommitAuthor)) {
                    commits.add(currentCommit);
                }
                else {
                    wasLastCheckedCommitAuthorSameAsLastCommit = false;
                }
            }
        }

        return commits;
    }

    private static String getRulesForCommitMessageFormatting() {
        return "Proper commit message should adhere to the following rules:\n"
                + "    1) Must match one of the following patterns:\n"
                + "        " + ISSUE_COMMIT_MESSAGE_REGEX_PATTERN + "\n"
                + "        " + PR_COMMIT_MESSAGE_REGEX_PATTERN + "\n"
                + "        " + OTHER_COMMIT_MESSAGE_REGEX_PATTERN + "\n"
                + "    2) It contains only one line";
    }

    private static String getInvalidCommitMessageFormattingError(String commitId,
            String commitMessage) {
        return "Commit " + commitId + " message: \"" + commitMessage + "\" is invalid\n"
                + getRulesForCommitMessageFormatting();
    }

    private static class RevCommitsPair {
        private final Iterator<RevCommit> first;
        private final Iterator<RevCommit> second;

        RevCommitsPair() {
            first = Collections.emptyIterator();
            second = Collections.emptyIterator();
        }

        RevCommitsPair(Iterator<RevCommit> first, Iterator<RevCommit> second) {
            this.first = first;
            this.second = second;
        }

        public Iterator<RevCommit> getFirst() {
            return first;
        }

        public Iterator<RevCommit> getSecond() {
            return second;
        }
    }

    private static class OmitMergeCommitsIterator implements Iterator<RevCommit> {

        private final Iterator<RevCommit> revCommitIterator;

        OmitMergeCommitsIterator(Iterator<RevCommit> revCommitIterator) {
            this.revCommitIterator = revCommitIterator;
        }

        @Override
        public boolean hasNext() {
            return revCommitIterator.hasNext();
        }

        @Override
        public RevCommit next() {
            RevCommit currentCommit = revCommitIterator.next();
            while (isMergeCommit(currentCommit)) {
                currentCommit = revCommitIterator.next();
            }
            return currentCommit;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}

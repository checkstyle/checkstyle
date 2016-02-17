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

import com.google.common.base.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.github.GitHub;

/**
 * Verifies that pull request description contains issue number.
 *
 * @author <a href="mailto:kordas.michal@gmail.com">Michal Kordas</a>
 */
public class PullRequestDescriptionTest {

    /**
     * @noinspection CallToSystemGetenv
     */
    @Test
    public void testPullRequestDescription() throws Exception {
        try (Git git = new Git(new FileRepositoryBuilder().findGitDir().build())) {
            final String travisPullRequest = System.getenv("TRAVIS_PULL_REQUEST");
            final Pattern pattern = Pattern.compile("#\\d+");
            final Optional<String> pullRequestId = Optional.fromNullable(travisPullRequest);
            final Iterable<RevCommit> commits = git.log().call();
            if (pullRequestId.isPresent()) {
                for (RevCommit commit : commits) {
                    final String shortMessage = commit.getShortMessage();
                    System.out.println("shortMessage = " + shortMessage);
                    final Matcher matcher = pattern.matcher(shortMessage);
                    System.out.println("pullRequestId = " + pullRequestId);
                    if (matcher.find()) {
                        final String issueId = matcher.group();
                        final String body = GitHub.connectAnonymously()
                            .getRepository("checkstyle/checkstyle")
                            .getPullRequest(Integer.parseInt(pullRequestId.get())).getBody();
                        Assert.assertTrue(
                            String.format(
                                "Issue %s is not mentioned in pull request %s description: '%s'",
                                issueId, pullRequestId, body
                            ),
                            body.contains(issueId)
                        );
                        break;
                    }
                }
            }
        }
    }
}

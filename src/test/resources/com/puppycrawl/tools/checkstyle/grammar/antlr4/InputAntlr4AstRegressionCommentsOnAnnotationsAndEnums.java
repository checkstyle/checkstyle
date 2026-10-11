/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import static com.puppycrawl.tools.checkstyle.grammar.antlr4.LifecyclePhase.GENERATE_RESOURCES;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.platform.commons.logging.Logger;

class Artifact{}
class ArtifactRepository{}
class License{}
class Model{}
class Organization{}
class ProcessRemoteResourcesMojo{}
@interface Component{
    Class<MavenProjectBuilder> role();
}
class LifecyclePhase{
    public static final Object GENERATE_RESOURCES = null;
}
@interface Mojo{
    String name();
}
@interface Parameter{
    String defaultValue();

    boolean readonly();
}
class ResolutionScope{
    public static final Object RUNTIME = null;
}
class InvalidProjectModelException{}
class MavenProject{}
class MavenProjectBuilder{}
class ProjectBuildingException{}

/**
 * Extends the Maven Remote Resources plugin to fix memory issue found in it, see
 * <a href="https://jira.xwiki.org/browse/XCOMMONS-1421">XCOMMONS-1421</a>.
 * <p>
 * To be removed when <a href="https://issues.apache.org/jira/browse/MRRESOURCES-106">MRRESOURCES-106</a> is fixed on
 * the Maven side.
 *
 * @version $Id: 45afe6a790dc2b72fec66a9f5b0e95e95105bd43 $
 * @since 9.11.5
 * @since 10.5RC1
 */
@Mojo(name = "")
public class InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums
        extends ProcessRemoteResourcesMojo
{
    /**
     * Control what is injected in the "projects" Velocity binding.
     *
     * @version $Id: 45afe6a790dc2b72fec66a9f5b0e95e95105bd43 $
     */
    public enum ProjectData
    {
        /**
         * "projects" is empty.
         */
        NONE,

        /**
         * "projects" only contains licenses.
         */
        LICENSES,

        /**
         * "projects" contains full MavenProject metadata
         * (very expensive but standard Maven Resource plugin behavior).
         */
        FULL
    }

    @Parameter(defaultValue = "NONE", readonly = false)
    protected ProjectData projectsData;

    @Parameter(defaultValue = "${localRepository}", readonly = true)
    private ArtifactRepository localRepositoryThis;

    @Component(role = MavenProjectBuilder.class)
    private MavenProjectBuilder mavenProjectBuilderThis;

    /**
     * List of Remote Repositories used by the resolver.
     */
    @Parameter(defaultValue = "${project.remoteArtifactRepositories}", readonly = true)
    private List<ArtifactRepository> remoteArtifactRepositoriesThis;

    private Logger getLog() {
        return null;
    }

    enum InputJavadocVariableTagsEnum
    {
        CONSTANT_A, // violation

        /**
         *
         */
        CONSTANT_B,

        CONSTANT_C // violation
        {
            /**
             *
             */
            public void someMethod()
            {
            }

            public void someOtherMethod()
            {

            }
        }
    }

}

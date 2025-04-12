# Checkstyle - Java Code Quality Tool

![](https://raw.githubusercontent.com/checkstyle/resources/master/img/checkstyle-logos/checkstyle-logo-260x99.png)

--------------------------

*Checkstyle is a tool that ensures adherence to a code standard or a set of best practices.*

[![][appveyor img]][appveyor]
[![][circleci img]][circleci]
[![][cirrusci img]][cirrusci]
[![][coverage img]][coverage]
[![][snyk img]][snyk]
[![][semaphoreci img]][semaphoreci]
[![][azure img]][azure]
[![][error prone img]][error prone]
[![][pitest img]][pitest]
[![][checker framework img]][checker framework]
[![][dependabot img]][dependabot]
[![][sonar img]][sonar]
[![][release notes/version img]][release notes/version]
[![][closed issues img]][closed issues]
[![][link check img]][link check]
[![][milestone img]][milestone]

[![][mavenbadge img]][mavenbadge]

The latest release version can be found at
[GitHub releases](https://github.com/checkstyle/checkstyle/releases/)
or at [Maven repo](https://repo1.maven.org/maven2/com/puppycrawl/tools/checkstyle/).

Each-commit builds of maven artifacts can be found at
[Maven Snapshot repository](https://oss.sonatype.org/content/repositories/snapshots/com/puppycrawl/tools/checkstyle/).

Documentation is available in HTML format, see https://checkstyle.org/checks.html .

## Table of Contents

- [Quick Start](#quick-start)
- [Contributing](#contributing)
- [Feedback and Support](#feedback-and-support)
- [Javadoc](#javadoc)
- [Sponsor Checkstyle](#sponsor-checkstyle)
- [Licensing](#licensing)

## Quick Start

- Download our [Latest Release](https://github.com/checkstyle/checkstyle/releases/) from GitHub
  or Add Checkstyle to your build from [Maven Central](https://mvnrepository.com/artifact/com.puppycrawl.tools/checkstyle).
- Read our Documentation for [usage](https://checkstyle.org/cmdline.html)
  and [configuration](https://checkstyle.org/config.html).

```bash
$ cat config.xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
          "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
          "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
  <module name="TreeWalker">
    <module name="FallThrough"/>
  </module>
</module>

$ cat Test.java
class Test {
  public void foo() {
    int i = 0;
    while (i >= 0) {
      switch (i) {
        case 1:
        case 2:
          i++;
        case 3: // violation 'fall from previous branch of the switch'
          i++;
      }
    }
  }
}

$ java -jar checkstyle-10.18.1-all.jar -c config.xml Test.java
Starting audit...
[ERROR] Test.java:9:9: Fall through from previous branch of switch statement [FallThrough]
Audit done.
Checkstyle ends with 1 errors.
```

## Contributing

Thanks for your interest in contributing to CheckStyle! Please see the
[Contribution Guidelines](https://github.com/checkstyle/checkstyle/blob/master/.github/CONTRIBUTING.md)
for information on how to contribute to the project. This includes creating issues, submitting pull
requests, and setting up your development environment.

## Build Instructions

Please see the [CheckStyle Documentation](https://checkstyle.org/contributing.html#Build) for
information on how to build the project.

## Feedback and Support

- Visit our [Discussions Page](https://github.com/checkstyle/checkstyle/discussions), where you
  can ask questions and discuss the project with other users and contributors. This is our
  preferred method of communication for topics
  like usage and configuration questions, debugging, and other feedback.
- [Stack Overflow](https://stackoverflow.com/questions/tagged/checkstyle) is another place to
  ask questions about Checkstyle usage.
- If you are interested in contributing to the project, you can join our
  [Discord Contributors Chat](https://discord.com/channels/845645228467159061/1216455699488313554)
  [with invite link](https://discord.gg/FsUsYC2ura).
- Our [Google Groups Forum](https://groups.google.com/forum/?hl=en#!forum/checkstyle) is a
  mailing list for discussion and support; however, we may be slow to respond there.

## Javadoc

Take a look at our [javadoc](https://checkstyle.org/apidocs/index.html) to see
our API documentation.

## Sponsor Checkstyle

Checkstyle is an open-source project that is developed and maintained by volunteers. If you
find Checkstyle useful, please consider sponsoring the project. Your support helps us to
maintain and improve Checkstyle.

- [Liberapay](https://liberapay.com/checkstyle/)
- [OpenCollective](https://opencollective.com/checkstyle/)

[![][backers.opencollective img]][backers.opencollective]

[![][sponsors.opencollective img]][sponsors.opencollective]

## Licensing

Checkstyle is licensed under the [GNU LGPL v2.1 License](LICENSE).
Checkstyle uses libraries:

- [ANTLR](https://www.antlr.org/)
- [Apache Commons](https://commons.apache.org/)
- [Google Guava](https://github.com/google/guava/)
- [Picocli](https://github.com/remkop/picocli/)

[appveyor]:https://ci.appveyor.com/project/checkstyle/checkstyle/history
[appveyor img]:https://ci.appveyor.com/api/projects/status/rw6bw3dl9kph6ucc?svg=true

[sonar]:https://sonarcloud.io/dashboard?id=org.checkstyle%3Acheckstyle
[sonar img]:https://sonarcloud.io/api/project_badges/measure?project=org.checkstyle%3Acheckstyle&metric=sqale_index

[coverage]:https://codecov.io/github/checkstyle/checkstyle?branch=master
[coverage img]:https://codecov.io/github/checkstyle/checkstyle/coverage.svg?branch=master

[mavenbadge]:https://search.maven.org/search?q=g:%22com.puppycrawl.tools%22%20AND%20a:%22checkstyle%22
[mavenbadge img]:https://img.shields.io/maven-central/v/com.puppycrawl.tools/checkstyle.svg?label=Maven%20Central

[stackoverflow]:https://stackoverflow.com/questions/tagged/checkstyle
[stackoverflow img]:https://img.shields.io/badge/stackoverflow-CHECKSTYLE-blue.svg

[teamcity]:https://teamcity.jetbrains.com/viewType.html?buildTypeId=Checkstyle_IdeaInspectionsMaster
[teamcity img]:https://teamcity.jetbrains.com/app/rest/builds/buildType:(id:Checkstyle_IdeaInspectionsMaster)/statusIcon

[circleci]: https://circleci.com/gh/checkstyle/checkstyle/tree/master
[circleci img]: https://circleci.com/gh/checkstyle/checkstyle/tree/master.svg?style=svg

[cirrusci]: https://cirrus-ci.com/github/checkstyle/checkstyle
[cirrusci img]: https://api.cirrus-ci.com/github/checkstyle/checkstyle.svg?branch=master

[snyk]: https://snyk.io/test/github/checkstyle/checkstyle?targetFile=pom.xml
[snyk img]: https://snyk.io/test/github/checkstyle/checkstyle/badge.svg

[semaphoreci]: https://checkstyle.semaphoreci.com/projects/checkstyle
[semaphoreci img]: https://checkstyle.semaphoreci.com/badges/checkstyle/branches/master.svg?style=shields

[azure]:https://dev.azure.com/romanivanovjr/romanivanovjr/_build/latest?definitionId=1&branchName=master
[azure img]:https://dev.azure.com/romanivanovjr/romanivanovjr/_apis/build/status/checkstyle.checkstyle?branchName=master

[backers.opencollective]:https://opencollective.com/checkstyle/
[backers.opencollective img]:https://opencollective.com/checkstyle/backers/badge.svg

[sponsors.opencollective]:https://opencollective.com/checkstyle/
[sponsors.opencollective img]:https://opencollective.com/checkstyle/sponsors/badge.svg

[dependabot]:https://github.com/dependabot
[dependabot img]:https://img.shields.io/badge/dependabot-025E8C?style=for-the-badge&logo=dependabot

[closed issues]:https://github.com/checkstyle/checkstyle/actions/workflows/no-old-refs.yml
[closed issues img]:https://github.com/checkstyle/checkstyle/actions/workflows/no-old-refs.yml/badge.svg

[release notes/version]:https://github.com/checkstyle/checkstyle/actions/workflows/releasenotes-gen.yml
[release notes/version img]:https://github.com/checkstyle/checkstyle/actions/workflows/releasenotes-gen.yml/badge.svg

[link check]:https://github.com/checkstyle/checkstyle/actions/workflows/run-link-check.yml
[link check img]:https://github.com/checkstyle/checkstyle/actions/workflows/run-link-check.yml/badge.svg

[error prone]:https://github.com/checkstyle/checkstyle/actions/workflows/error-prone.yml
[error prone img]:https://github.com/checkstyle/checkstyle/actions/workflows/error-prone.yml/badge.svg

[pitest]:https://github.com/checkstyle/checkstyle/actions/workflows/pitest.yml
[pitest img]:https://github.com/checkstyle/checkstyle/actions/workflows/pitest.yml/badge.svg

[checker framework]:https://github.com/checkstyle/checkstyle/actions/workflows/checker-framework.yml
[checker framework img]:https://github.com/checkstyle/checkstyle/actions/workflows/checker-framework.yml/badge.svg

[milestone]:https://github.com/checkstyle/checkstyle/actions/workflows/set-milestone-on-referenced-issue.yml
[milestone img]:https://github.com/checkstyle/checkstyle/actions/workflows/set-milestone-on-referenced-issue.yml/badge.svg

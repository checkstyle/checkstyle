# Checkstyle - Java Code Quality Tool

![](https://raw.githubusercontent.com/checkstyle/resources/master/img/checkstyle-logos/checkstyle-logo-260x99.png)

*Checkstyle is a tool that ensures adherence to a code standard, or a set of best practices.*

---

Continuous Integration:

- [AppVeyor ![][appveyor img]][appveyor]
- [Azure ![][azure img]][azure]
- [Checker Framework ![][checker framework img]][checker framework]
- [Circle CI ![][circleci img]][circleci]
- [Cirrus CI ![][cirrusci img]][cirrusci]
- [Closed Issues ![][closed issues img]][closed issues]
- [Dependabot ![][dependabot img]][dependabot]
- [Link Check ![][link check img]][link check]
- [Milestone Automation ![][milestone img]][milestone]
- [Release Notes ![][release notes/version img]][release notes/version]

Quality Assurance:

- [Codecov ![][coverage img]][coverage]
- [Error Prone ![][error prone img]][error prone]
- [OpenRewrite ![][rewrite img]][rewrite]
- [PIT ![][pitest img]][pitest]
- [Semaphore CI ![][semaphoreci img]][semaphoreci]
- [Snyk ![][snyk img]][snyk]
- [Spotless ![][spotless img]][spotless]

Release:

- [Maven Central ![][mavenbadge img]][mavenbadge]

---

- The latest release version can be found at [GitHub releases](https://github.com/checkstyle/checkstyle/releases/)
  or at [Maven repo](https://repo1.maven.org/maven2/com/puppycrawl/tools/checkstyle/).
- Documentation is [available](https://checkstyle.org/checks.html) in HTML format.

---

## Table of Contents

- [Quick Start](#quick-start)
- [Contributing](#contributing)
- [Feedback and Support](#feedback-and-support)
- [Javadoc](#javadoc)
- [Sponsor Checkstyle](#sponsor-checkstyle)
- [Licensing](#licensing)
- [Tools](#tools)

---

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

---

## Contributing

Thanks for your interest in contributing to CheckStyle! Please see the
[Contribution Guidelines](https://github.com/checkstyle/checkstyle/blob/master/.github/CONTRIBUTING.md)
for information on how to contribute to the project. This includes creating issues, submitting pull
requests, and setting up your development environment.

---

## Build Instructions

Please see the [CheckStyle Documentation](https://checkstyle.org/contributing.html#Build) for
information on how to build the project.

---

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

---

## Javadoc

Take a look at our [javadoc](https://checkstyle.org/apidocs/index.html) to see
our API documentation.

---

## Sponsor Checkstyle

Checkstyle is an open-source project that is developed and maintained by volunteers.

Please consider sponsoring the project, if you find Checkstyle useful.
Your support helps us to maintain and improve Checkstyle.

- [Liberapay](https://liberapay.com/checkstyle/)
- [OpenCollective](https://opencollective.com/checkstyle/)

[![][backers.opencollective img]][backers.opencollective]
[![][sponsors.opencollective img]][sponsors.opencollective]

---

## Licensing

Checkstyle is licensed under the [GNU LGPL v2.1 License](LICENSE).

Checkstyle uses the following libraries:

- [ANTLR](https://www.antlr.org/)
- [Apache Commons️](https://commons.apache.org/)
- [Google Guava](https://github.com/google/guava/)
- [Picocli](https://github.com/remkop/picocli/)

---

## Tools

Checkstyle uses the following tools:

Continuous Integration:

- [AppVeyor](https://ci.appveyor.com/project/checkstyle/checkstyle)
- [Azure](https://dev.azure.com/checkstyle/checkstyle/_build)
- [Circle CI](https://circleci.com/gh/checkstyle/checkstyle)
- [Cirrus CI](https://cirrus-ci.com/github/checkstyle/checkstyle)
- [Dependabot](https://github.com/dependabot)
- [Link Check](https://github.com/checkstyle/checkstyle/actions/workflows/run-link-check.yml)
- [Milestone Automation](https://github.com/checkstyle/checkstyle/actions/workflows/set-milestone-on-referenced-issue.yml)
- [Release Notes](https://github.com/checkstyle/checkstyle/actions/workflows/releasenotes-gen.yml)
- [Semaphore CI](https://checkstyle.semaphoreci.com)
- [TeamCity](https://teamcity.jetbrains.com/project/Checkstyle)

Quality Assurance:

- [Checker Framework](https://checkerframework.org)
- [Codecov](https://codecov.io/gh/checkstyle/checkstyle)
- [Error Prone](https://errorprone.info)
- [OpenRewrite](https://docs.openrewrite.org)
- [PIT](https://pitest.org)
- [PMD](https://pmd.github.io)
- [Snyk](https://app.snyk.io/org/checkstyle)
- [SpotBugs](https://spotbugs.github.io)
- [Spotless](https://github.com/diffplug/spotless)

Release:

- [Maven Central](https://central.sonatype.com/artifact/com.puppycrawl.tools/checkstyle)

Development Tools:

- [JetBrains IDEs](https://www.jetbrains.com/opensource)
- [JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)

---

## Development Tools Powered by

- [![JetBrains logo.][jetbrains img]][jetbrains]
- [![JProfiler logo.][jprofiler img]][jprofiler]

---

[appveyor img]:https://ci.appveyor.com/api/projects/status/rw6bw3dl9kph6ucc?svg=true
[appveyor]:https://ci.appveyor.com/project/checkstyle/checkstyle/history
[azure img]:https://dev.azure.com/romanivanovjr/romanivanovjr/_apis/build/status/checkstyle.checkstyle?branchName=master
[azure]:https://dev.azure.com/romanivanovjr/romanivanovjr/_build/latest?definitionId=1&branchName=master
[backers.opencollective img]:https://opencollective.com/checkstyle/backers/badge.svg
[backers.opencollective]:https://opencollective.com/checkstyle/
[checker framework img]:https://github.com/checkstyle/checkstyle/actions/workflows/checker-framework.yml/badge.svg
[checker framework]:https://github.com/checkstyle/checkstyle/actions/workflows/checker-framework.yml
[circleci img]: https://circleci.com/gh/checkstyle/checkstyle/tree/master.svg?style=svg
[circleci]: https://circleci.com/gh/checkstyle/checkstyle/tree/master
[cirrusci img]: https://api.cirrus-ci.com/github/checkstyle/checkstyle.svg?branch=master
[cirrusci]: https://cirrus-ci.com/github/checkstyle/checkstyle
[closed issues img]:https://github.com/checkstyle/checkstyle/actions/workflows/no-old-refs.yml/badge.svg
[closed issues]:https://github.com/checkstyle/checkstyle/actions/workflows/no-old-refs.yml
[coverage img]:https://codecov.io/github/checkstyle/checkstyle/coverage.svg?branch=master
[coverage]:https://codecov.io/github/checkstyle/checkstyle?branch=master
[dependabot img]:https://img.shields.io/badge/dependabot-025E8C?style=for-the-badge&logo=dependabot
[dependabot]:https://github.com/dependabot
[error prone img]:https://github.com/checkstyle/checkstyle/actions/workflows/error-prone.yml/badge.svg
[error prone]:https://github.com/checkstyle/checkstyle/actions/workflows/error-prone.yml
[jetbrains img]:https://resources.jetbrains.com/storage/products/company/brand/logos/jetbrains.svg
[jetbrains]:https://jb.gg/OpenSource
[jprofiler img]:https://www.ej-technologies.com/images/product_banners/jprofiler_medium.png
[jprofiler]:https://www.ej-technologies.com/jprofiler
[link check img]:https://github.com/checkstyle/checkstyle/actions/workflows/run-link-check.yml/badge.svg
[link check]:https://github.com/checkstyle/checkstyle/actions/workflows/run-link-check.yml
[mavenbadge img]:https://img.shields.io/maven-central/v/com.puppycrawl.tools/checkstyle.svg?label=Maven%20Central
[mavenbadge]:https://search.maven.org/search?q=g:%22com.puppycrawl.tools%22%20AND%20a:%22checkstyle%22
[milestone img]:https://github.com/checkstyle/checkstyle/actions/workflows/set-milestone-on-referenced-issue.yml/badge.svg
[milestone]:https://github.com/checkstyle/checkstyle/actions/workflows/set-milestone-on-referenced-issue.yml
[pitest img]:https://github.com/checkstyle/checkstyle/actions/workflows/pitest.yml/badge.svg
[pitest]:https://github.com/checkstyle/checkstyle/actions/workflows/pitest.yml
[release notes/version img]:https://github.com/checkstyle/checkstyle/actions/workflows/releasenotes-gen.yml/badge.svg
[release notes/version]:https://github.com/checkstyle/checkstyle/actions/workflows/releasenotes-gen.yml
[rewrite img]:https://github.com/checkstyle/checkstyle/actions/workflows/rewrite.yml/badge.svg
[rewrite]:https://github.com/checkstyle/checkstyle/actions/workflows/rewrite.yml
[semaphoreci img]: https://checkstyle.semaphoreci.com/badges/checkstyle/branches/master.svg?style=shields
[semaphoreci]: https://checkstyle.semaphoreci.com/projects/checkstyle
[snyk img]: https://snyk.io/test/github/checkstyle/checkstyle/badge.svg
[snyk]: https://snyk.io/test/github/checkstyle/checkstyle?targetFile=pom.xml
[sponsors.opencollective img]:https://opencollective.com/checkstyle/sponsors/badge.svg
[sponsors.opencollective]:https://opencollective.com/checkstyle/
[spotless img]:https://github.com/checkstyle/checkstyle/actions/workflows/spotless.yml/badge.svg
[spotless]:https://github.com/checkstyle/checkstyle/actions/workflows/spotless.yml
[stackoverflow img]:https://img.shields.io/badge/stackoverflow-CHECKSTYLE-blue.svg
[stackoverflow]:https://stackoverflow.com/questions/tagged/checkstyle
[teamcity img]:https://teamcity.jetbrains.com/app/rest/builds/buildType:(id:Checkstyle_IdeaInspectionsMaster)/statusIcon
[teamcity]:https://teamcity.jetbrains.com/viewType.html?buildTypeId=Checkstyle_IdeaInspectionsMaster

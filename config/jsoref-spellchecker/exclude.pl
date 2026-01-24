#!/usr/bin/perl
# This script takes null delimited files as input
# it drops paths that match the listed exclusions
# output is null delimited to match input
$/="\0";
my @excludes=qw(
  (^|/)images/
  /.*_..\.translation[^/]*$
  /messages.*_..\.properties$
  /releasenotes\.xml$
  /releasenotes_old.*\.xml$
  \.png$
  ^.teamcity/
  ^cdg-pitest-licence.txt$
  ^config/archunit-store/
  ^config/checker-framework-suppressions/
  ^config/jsoref-spellchecker/whitelist.words$
  ^config/projects-to-test/openjdk17-excluded\.files$
  ^config/projects-to-test/openjdk19-excluded\.files$
  ^config/projects-to-test/openjdk20-excluded\.files$
  ^config/projects-to-test/openjdk25-excluded\.files$
  ^config/sarif-schema-2.1.0.json$
  ^rewrite.yml$
  ^pom.xml$
  ^src/it/resources/
  ^src/site/resources/styleguides/
  ^src/test/resources-noncompilable/
  ^src/test/resources/
);
my $exclude = join "|", @excludes;
while (<>) {
  chomp;
  next if m{$exclude};
  print "$_$/";
}

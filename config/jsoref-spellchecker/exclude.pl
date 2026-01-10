#!/usr/bin/perl
# This script takes null delimited files as input
# it drops paths that match the listed exclusions
# output is null delimited to match input
$/="\0";
my @excludes=qw(
  \.png$
  (^|/)images/
  ^src/it/resources/
  ^src/test/resources/
  ^src/site/resources/styleguides/
  ^src/test/resources-noncompilable/
  /messages.*_..\.properties$
  /releasenotes_old.*\.xml$
  /releasenotes\.xml$
  /.*_..\.translation[^/]*$
  ^cdg-pitest-licence.txt$
  ^.teamcity/
  ^config/projects-to-test/openjdk17-excluded\.files$
  ^config/projects-to-test/openjdk19-excluded\.files$
  ^config/projects-to-test/openjdk20-excluded\.files$
  ^config/projects-to-test/openjdk25-excluded\.files$
  ^config/jsoref-spellchecker/whitelist.words$
  ^config/checker-framework-suppressions/
  ^config/archunit-store/
  ^config/sarif-schema-2.1.0.json$
);
my $exclude = join "|", @excludes;
while (<>) {
  chomp;
  next if m{$exclude};
  print "$_$/";
}

#!/bin/bash
set -e

printf "%s\n" "<settings>
    <servers>
        <server>
            <id>sonatype-nexus-snapshots</id>
            <username>romanivanov</username>
            <password>password</password>
        </server>
        <server>
            <id>sonatype-nexus-staging</id>
            <username>romanivanov</username>
            <password>password</password>
        </server>
        <server>
            <id>sourceforge-new-site</id>
            <username>romanivanov</username>
            <password>password</password>
        </server>
    </servers>

    <profiles>
      <profile>
          <!-- "mvn release:prepare .... -Pgpg"
               and it is possible to skip " -Dgpg.passphrase=xxxx" at "-Darguments"
               # shellcheck disable=SC1001
               Use "gpg '\-\-'list-keys", "pub   1024D/C6EED57A 2010-01-13"
                    to get "gpg.keyname" in example it is  value "C6EED57A"
          -->
          <id>gpg</id>
          <properties>
              <gpg.passphrase>X</gpg.passphrase>
              <gpg.keyname>X</gpg.keyname>
          </properties>
      </profile>
    </profiles>
</settings>" > ~/.m2/settings.xml
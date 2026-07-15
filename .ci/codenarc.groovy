
,,


import java.lang.Object

org.codenarc.CodeNarc.main([
        "-basedir=${args[0]}",
        "-includes=**/${args[1]}",
        '-rulesetfiles=./config/codenarc-rules.groovy.txt',
        '-report=console',
] as String[])

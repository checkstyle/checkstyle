#!/bin/bash
# This CI acceptance test is based on:
# https://github.com/jsoref/spelling/blob/04648bdc63723e5cdf5cbeaff2225a462807abc8
# It is conceptually `f` which runs `w` (spelling-unknown-word-splitter)
# plus `fchurn` which uses `dn` mostly rolled together.
set -e

spellchecker=.ci/jsoref-spellchecker
whitelist_path=.ci/jsoref-spellchecker/whitelist.words
dict=$spellchecker/english.words
word_splitter=$spellchecker/spelling-unknown-word-splitter.pl
run_output=$spellchecker/unknown.words
if [ ! -e $dict ]; then
  echo "Retrieve ./usr/share/dict/linux.words"
  words_rpm=$spellchecker/words.rpm
  URL_PART1="https://rpmfind.net/linux/fedora/linux/development/rawhide/"
  URL_PART2="Everything/aarch64/os/Packages/w/words-3.0-28.fc28.noarch.rpm"
  curl $URL_PART1$URL_PART2 > $words_rpm
  $spellchecker/rpm2cpio.sh $words_rpm |\
    cpio -i --to-stdout ./usr/share/dict/linux.words > $dict
  rm $words_rpm
fi

if [ ! -e $word_splitter ]; then
  echo "Retrieve w"
  curl -s https://raw.githubusercontent.com/jsoref/spelling/master/w |\
    perl -p -n -e "s</usr/share/dict/words><$dict>" > $word_splitter
  chmod u+x $word_splitter
  echo "Retrieved."
  ls -la $word_splitter
fi

echo "Clean up from previous run"
rm -f $run_output

echo "Run w"
(git 'ls-files' -z 2> /dev/null || hg locate -0) |\
  .ci/jsoref-spellchecker/exclude.pl |\
  xargs -0 $word_splitter |\
  $word_splitter |\
  perl -p -n -e 's/ \(.*//' > $run_output

printDetails() {
  echo ''
  echo 'If you are ok with the output of this run, you will need to'
}

echo "Review results"
if [ ! -e $whitelist_path ]; then
  echo "No preexisting $whitelist_path file."
  printDetails
  echo "cat > $whitelist_path <<EOF=EOF"
  cat $run_output
  echo EOF=EOF
  exit 2
fi

diff_output=`diff -U1 $whitelist_path $run_output |grep -v "$spellchecker" || true`

if [ -z "$diff_output" ]; then
  echo "No new words and misspellings found."
  exit 0
fi

new_output=`diff -i -U0 $whitelist_path $run_output |grep -v "$spellchecker" |\
  perl -n -w -e 'next unless /^\+/; next if /^\+{3} /; s/^.//; print;'`
if [ -z "$new_output" ]; then
  echo "There are now fewer misspellings than before."
  echo "$whitelist_path could be updated:"
  echo ''
  echo "patch $whitelist_path <<EOF"
  echo "$diff_output"
  echo "EOF"
  sleep 5
  exit 1
fi
echo "New misspellings found, please review:"
echo "$new_output"
printDetails
echo "patch $whitelist_path <<EOF"
echo "$diff_output"
echo "EOF"
sleep 5
exit 1

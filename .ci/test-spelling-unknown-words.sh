#!/bin/bash
# This CI acceptance test is based on:
# https://github.com/jsoref/spelling/tree/04648bdc63723e5cdf5cbeaff2225a462807abc8
# It is conceptually `f` which runs `w` (spelling-unknown-word-splitter)
# plus `fchurn` which uses `dn` mostly rolled together.
set -e

spellchecker='config/jsoref-spellchecker'
temp='.ci-temp'
whitelist_path="$spellchecker/whitelist.words"
# english.words is taken from rpm:
# https://rpmfind.net/linux/fedora/linux/development/rawhide/Everything/aarch64/os/Packages/w/"
# "words-.*.noarch.rpm"
dict="$spellchecker/english.words"
word_splitter="$temp/spelling-unknown-word-splitter.pl"
run_output="$temp/unknown.words"

mkdir -p $temp

if [ ! -e "$word_splitter" ]; then
  echo "Retrieve w"
  w_location='https://raw.githubusercontent.com/jsoref/spelling/master/w'
  curl --fail-with-body -s "$w_location" |\
    perl -p -n -e "s</usr/share/dict/words><$dict>" > "$word_splitter"
  get_word_splitter_status="${PIPESTATUS[0]} ${PIPESTATUS[1]}"
  if [ "$get_word_splitter_status" != '0 0' ]; then
    echo "$0 failed to retrieve/adapt word splitter ($w_location) ($get_word_splitter_status)"
    rm -f "$word_splitter"
    exit 6
  fi
  chmod u+x "$word_splitter"
  echo "Retrieved."
  ls -la "$word_splitter"
fi

echo "Clean up from previous run"
rm -f "$run_output"

echo "Run w"
(git 'ls-files' -z 2> /dev/null || hg locate -0) |\
  "$spellchecker/exclude.pl" |\
  xargs -0 "$word_splitter" |\
  "$word_splitter" |\
  perl -p -n -e 's/ \(.*//' > "$run_output"
  word_splitter_status="${PIPESTATUS[2]} ${PIPESTATUS[3]}"
  if [ "$word_splitter_status" != '0 0' ]; then
    echo "$word_splitter failed ($word_splitter_status)"
    exit 2
  fi

printDetails() {
  echo ''
  echo 'If you are ok with the output of this run,'
  echo 'you will need to run the following entire multiline command:'
}

echo "Review results"
if [ ! -e "$whitelist_path" ]; then
  echo "No preexisting $whitelist_path file."
  printDetails
  echo 'cat > '"$whitelist_path"' <<EOF=EOF'
  cat "$run_output"
  echo EOF=EOF
  exit 2
fi

diff_output=$(diff -U1 "$whitelist_path" "$run_output" |grep -v "$spellchecker" || true)

if [ -z "$diff_output" ]; then
  echo "No new words and misspellings found."
  rm $word_splitter
  rm $run_output
  exit 0
fi

new_output=$(diff -i -U0 "$whitelist_path" "$run_output" |grep -v "$spellchecker" |\
  perl -n -w -e 'next unless /^\+/; next if /^\+{3} /; s/^.//; print;')
if [ -z "$new_output" ]; then
  echo "There are now fewer misspellings than before."
  echo "$whitelist_path could be updated:"
  echo ''
  echo "patch '$whitelist_path' <<EOF"
  echo "$diff_output"
  echo "EOF"
  sleep 5s
  exit 1
fi
echo "New misspellings found, please review:"
echo "$new_output"
printDetails
echo "patch $whitelist_path <<EOF"
echo "$diff_output"
echo "EOF"
sleep 5s
exit 1

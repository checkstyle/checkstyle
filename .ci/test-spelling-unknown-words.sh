#!/bin/sh
# This CI acceptance test is based on:
# https://github.com/jsoref/spelling/blob/04648bdc63723e5cdf5cbeaff2225a462807abc8
# It is conceptually `f` which runs `w` (spelling-unknown-word-splitter)
# plus `fchurn` which uses `dn` mostly rolled together.
set -e

contrib_repo=https://github.com/checkstyle/contribution.git
temp=`pwd`/.ci-temp
mkdir -p $temp
contrib=$temp/contribution

if [[ ${skipFetchRepo+x} ]]; then
  echo "[WARN] Existing $contrib will be used, no clone/fetch will happen"
else
  if [ ! -d $contrib ]; then
    echo "cloning contribution repo"
    git clone $contrib_repo $contrib
  else
    echo "fetching contribution repo"
    cd $contrib;
    git fetch; git reset --hard origin/master
    cd $temp/../
  fi
fi

spellchecker=$contrib/jsoref-spellchecker
whitelist_path=jsoref-spellchecker/whitelist.words
dict=$spellchecker/english.words
word_splitter=$spellchecker/spelling-unknown-word-splitter.pl
run_output=$spellchecker/unknown.words
whitelist=$contrib/$whitelist_path
if [ ! -e $dict ]; then
  echo "Retrieve ./usr/share/dict/linux.words"
  words_rpm=$spellchecker/words.rpm
  curl https://rpmfind.net/linux/fedora/linux/development/rawhide/Everything/aarch64/os/Packages/w/words-3.0-28.fc28.noarch.rpm > $words_rpm
  $spellchecker/rpm2cpio.sh $words_rpm |\
    cpio -i --to-stdout ./usr/share/dict/linux.words > $dict
  rm $words_rpm
fi

echo "Retrieve w"
if [ ! -e $word_splitter ]; then 
  curl -s https://raw.githubusercontent.com/jsoref/spelling/master/w |\
    perl -p -n -e "s</usr/share/dict/words><$dict>" > $word_splitter
  chmod +x $word_splitter
fi

echo "Clean up from previous run"
rm -f $run_output

echo "Run w"
(git 'ls-files' -z 2> /dev/null || hg locate -0) |\
  .ci-temp/contribution/jsoref-spellchecker/exclude.pl |\
  xargs -0 $word_splitter |\
  $word_splitter |\
  perl -p -n -e 's/ \(.*//' > $run_output

printDetails() {
  echo ''
  echo 'If you are ok with the output of this run, you will need to'
  echo "git clone $contrib_repo contribution; cd contribution;"
}

echo "Review results"
if [ ! -e $whitelist ]; then
  echo No preexisting $whitelist file.
  printDetails
  echo "cat > $whitelist_path <<EOF=EOF"
  cat $run_output
  echo EOF=EOF
  exit 2
fi
diff_output=`diff -U0 $whitelist $run_output |grep -v "$spellchecker" || true`
[ -z "$diff_output" ] && exit 0
new_output=`diff -i -U0 $whitelist $run_output |grep -v "$spellchecker" |\
  perl -n -w -e 'next unless /^\+/; next if /^\+{3} /; s/^.//; print;'`
if [ -z "$new_output" ]; then
  echo "There are now fewer misspellings than before."
  echo "$contrib_repo $whitelist_path could be updated:"
  echo ''
  echo "$diff_output"
  sleep 5
  exit 0
fi
echo New misspellings found, please review:
echo "$new_output"
printDetails
echo "patch $whitelist_path <<EOF"
echo "$diff_output"
echo "EOF"
sleep 5
exit 0

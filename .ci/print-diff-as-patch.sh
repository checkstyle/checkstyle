#!/bin/bash
set -e

if [ -z "$1" ]; then
  echo "patch file-path not provided"
  echo "Usage: $0 <output_file_path>"
  exit 1
fi

patch_file="$1"
echo "git diff > $patch_file"
git diff > "$patch_file"

echo 'If you are ok with diff of this run,'
echo 'you will need to run the following entire multiline command:'
echo "patch -p1 <<'EOF'"
cat "$patch_file"
echo "EOF"

exit 1

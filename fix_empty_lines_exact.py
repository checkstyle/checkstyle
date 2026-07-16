import subprocess
import os
import re

# Get unified diff
diff_output = subprocess.check_output(['git', 'diff', 'HEAD~1', '-U0']).decode('utf-8')

# We want to find lines added that are empty, and immediately follow a `{`
files_to_edit = {}
current_file = None
current_line = 0

for line in diff_output.splitlines():
    if line.startswith('+++ b/'):
        current_file = line[6:]
        if current_file not in files_to_edit:
            files_to_edit[current_file] = []
    elif line.startswith('@@'):
        # @@ -606,2 +606,3 @@
        m = re.match(r'^@@ -\d+(?:,\d+)? \+(\d+)(?:,\d+)? @@', line)
        if m:
            current_line = int(m.group(1))
    elif line.startswith('+') and not line.startswith('+++'):
        added_text = line[1:]
        files_to_edit[current_file].append((current_line, added_text))
        current_line += 1
    elif line.startswith('-') and not line.startswith('---'):
        pass
    else:
        # Context line (shouldn't happen with -U0 but just in case)
        if not line.startswith('\\') and not line.startswith('---') and not line.startswith('+++'):
            current_line += 1

# Now process the files
lines_removed = 0
for f, additions in files_to_edit.items():
    if not f.endswith('.java'): continue
    if not os.path.exists(f): continue
    
    with open(f, 'r') as file:
        lines = file.read().splitlines()
    
    # We want to remove empty lines that were ADDED, and the previous line ends with `{`
    # Since we are removing lines, we do it from bottom to top to avoid shifting line numbers
    additions.sort(reverse=True)
    
    for line_num, text in additions:
        if text.strip() == '':
            idx = line_num - 1 # 0-indexed
            if idx < len(lines) and idx > 0:
                if lines[idx].strip() == '':
                    # Check if previous line ends with {
                    prev_idx = idx - 1
                    while prev_idx >= 0 and lines[prev_idx].strip() == '':
                        prev_idx -= 1
                    
                    if prev_idx >= 0 and lines[prev_idx].strip().endswith('{'):
                        # Remove the line
                        del lines[idx]
                        lines_removed += 1
    
    with open(f, 'w') as file:
        file.write('\n'.join(lines) + '\n')

print(f"Removed {lines_removed} lines.")

import os

def remove_ok_only_comments(directory):
    """
    Scans Java files in the specified directory whose names contain 'input' (case-insensitive)
    and removes single-line comments (`//`) that only contain 'ok'.
    """
    comment_prefix = "//"  # Java single-line comment prefix

    for root, _, files in os.walk(directory):
        for file in files:
            if not file.lower().endswith(".java") or "input" not in file.lower():
                continue  # Process only Java files with 'input' in the file name (case-insensitive)

            file_path = os.path.join(root, file)

            try:
                with open(file_path, 'r') as f:
                    lines = f.readlines()

                updated_lines = []
                for line in lines:
                    stripped_line = line.strip()
                    # Check if the line is a single-line comment with "ok" only
                    if stripped_line.startswith(comment_prefix) and stripped_line[len(comment_prefix):].strip().lower() == "ok":
                        continue  # Skip this line
                    updated_lines.append(line)

                # Write the updated content back to the file
                with open(file_path, 'w') as f:
                    f.writelines(updated_lines)

                print(f"Processed file: {file_path}")

            except Exception as e:
                print(f"Error processing file {file_path}: {e}")


if __name__ == "__main__":
    # Specify the directory path here
    directory_path = r"C:\OpenSource\checkstyle"
    print(f"Scanning directory: {os.path.abspath(directory_path)}")
    remove_ok_only_comments(directory_path)

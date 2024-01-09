package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class which provides OS related utilities.
 */
public final class OsSpecificUtil {
  /**
   * Updates the specified directory by resolving symbolic links, ensuring it exists,
   * and creating any necessary parent directories. If the provided path is a symbolic
   * link, it resolves it to the actual directory, throwing an IOException if the
   * resolved path is not a directory. Creates directories if they do not exist.
   *
   * @param directory The path to the directory to be updated.
   * @throws IOException If an I/O error occurs or if the resolved symbolic link is
   *                     not a directory.
   */
  public static void updateDirectory(Path directory) throws IOException {
    Path targetDirectory = directory;

    if (Files.isSymbolicLink(directory)) {
      final Path actualDir = directory.toRealPath();

      if (Files.isDirectory(actualDir)) {
        targetDirectory = actualDir;
      } else {
        throw new IOException(
            "Resolved symbolic link " + directory
                + " is not a directory.");
      }
    }
    Files.createDirectories(targetDirectory);
  }
}

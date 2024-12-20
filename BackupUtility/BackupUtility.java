import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class BackupUtility {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter source directory: ");
        String sourceDir = scanner.nextLine();
        
        System.out.print("Enter backup directory: ");
        String backupDir = scanner.nextLine();

        File source = new File(sourceDir);
        File backup = new File(backupDir);

        if (!source.isDirectory()) {
            System.out.println("Source path is not a directory: " + sourceDir);
            return;
        }

        if (!backup.exists()) {
            backup.mkdirs();
        }

        try {
            backupFiles(source.toPath(), backup.toPath());
            System.out.println("Backup completed successfully.");
        } catch (IOException e) {
            System.err.println("Error during backup: " + e.getMessage());
        }
    }

    private static void backupFiles(Path source, Path backup) throws IOException {
        Files.walk(source)
                .forEach(sourcePath -> {
                    Path destinationPath = backup.resolve(source.relativize(sourcePath));
                    try {
                        if (Files.isDirectory(sourcePath)) {
                            Files.createDirectories(destinationPath);
                        } else {
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        System.err.println("Error copying file: " + e.getMessage());
                    }
                });
    }
}

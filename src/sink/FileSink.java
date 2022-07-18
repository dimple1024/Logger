package sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSink implements Sink{

    private static Integer zippedFileCount = 0;
    private String filePath;
    private Long maxFileSize;

    public FileSink (String filePath, Long maxFileSize) {
        this.filePath = filePath;
        this.maxFileSize = maxFileSize;
    }

    @Override
    public void doSink(String message) throws IOException {
        // TODO: this needs to handled for concurrency and efficient write
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file,true);
        System.out.println(message + " received in fileSink!");
        long fileSizeInBytes = Files.size(Paths.get(this.filePath));
        if ((fileSizeInBytes + message.length()) <= this.maxFileSize )
        {
            fileWriter.write(message);
            fileWriter.write("\n");
            fileWriter.close();

        }
        else {
            // Rename and zip of old file
            zippedFileCount += 1;
            File oldFile = new File(this.filePath + "." + zippedFileCount + ".gz");
            file.renameTo(oldFile);
            new FileSink(this.filePath, this.maxFileSize);

        }

    }
}

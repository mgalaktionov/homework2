package ru.digitalhabbits.homework2;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

public class DataGenerator {

    private final Logger logger = getLogger(DataGenerator.class);
    private final char[] latinAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final static String RESOURCES_DIR = System.getProperty("user.dir") + "/rsc/";
    private final static String FILE_EXT = ".txt";
    private final String processingFileName;
    private final String resultFileName;
    private final File processingFile;
    private final File resultFile;

    public DataGenerator(String processingFileName, String resultFileName)
            throws IOException {
        this.processingFileName = processingFileName;
        this.resultFileName = resultFileName;
        deleteResources();
        this.processingFile = createFile(processingFileName);
        this.resultFile = createFile(resultFileName);
    }

    public void generate() throws IOException {
        var count = new Random().nextInt(1000) + 1;
        var builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            var strSize = new Random().nextInt(15) + 1;
            builder.append(i + 1)
                    .append(')')
                    .append(" ")
                    .append(generateRandomString(strSize))
                    .append('\n');
        }
        FileWriter writer = new FileWriter(processingFile);
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }

    private String generateRandomString(int size) {
        var builder = new StringBuilder(size);
        var random = new Random();
        for (int i = 0; i < size; i++) {
            var letter = latinAlphabet[random.nextInt(size)];
            builder.append(letter);
        }
        return builder.toString();
    }

    private File createFile(String fileName) throws IOException {
        var file = new File(RESOURCES_DIR + fileName + FILE_EXT);
        file.createNewFile();
        logger.info("Created new file '{}", file);
        return file;
    }

    private void deleteResources() {
        logger.info("Clearing resource directory...");
        final File resourceDir = new File(RESOURCES_DIR);
        if (resourceDir.list() != null) {
            stream(resourceDir.list((dir, name) -> name.endsWith(FILE_EXT)))
                    .forEach(fileName -> new File(resourceDir + "/" + fileName).delete());
        }
    }

    public File getProcessingFile() {
        return processingFile;
    }

    public File getResultFile() {
        return resultFile;
    }
}

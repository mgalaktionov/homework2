package ru.digitalhabbits.homework2;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

public class Homework {
    private static final Logger logger = getLogger(Homework.class);
    public static void main(String[] args) {

        final String processingFileName = getProcessingFileName(args);
        final String resultFileName = getResultFileName(args);
        DataGenerator generator = null;
        try {
            generator = new DataGenerator(processingFileName, resultFileName);
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException was cought during generation of input data, programm wil exit");
            System.exit(-1);
        }
        final File processingFile = generator.getProcessingFile();
        final File resultFile = generator.getResultFile();
        new FileProcessor()
                .process(processingFile, resultFile);
    }

    @Nonnull
    private static String getProcessingFileName(String[] args) {
        return getFileName(args, 0, "Enter processing file: ");
    }

    @Nonnull
    private static String getResultFileName(String[] args) {
        return getFileName(args, 1, "Enter result file: ");
    }

    @Nonnull
    private static String getFileName(String[] args, int index, String message) {
        if (args.length >= index + 1) {
            return args[index];
        }
        final Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

}

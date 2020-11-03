package ru.digitalhabbits.homework2;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Runtime.getRuntime;
import static java.nio.charset.Charset.defaultCharset;
import static org.slf4j.LoggerFactory.getLogger;

public class FileProcessor {
    private static final Logger logger = getLogger(FileProcessor.class);
    public static final int CHUNK_SIZE = 2 * getRuntime().availableProcessors();

    public void process(@Nonnull File processingFile, @Nonnull File resultFile) {
        checkFileExists(processingFile);
        // TODO: NotImplemented: запускаем FileWriter в отдельном потоке
//        Thread fileWriterThread = new Thread(new FileWriter());
//        fileWriterThread.start();
        logger.info("CHUNK SIZE = '{}", CHUNK_SIZE);
        var executor = Executors.newFixedThreadPool(CHUNK_SIZE);
        try (final Scanner scanner = new Scanner(processingFile, defaultCharset())) {
            var counter = 0;
            var taskList = new ArrayList<Future<Pair<String, Integer>>>();


            while (scanner.hasNext()) {
                if(counter < CHUNK_SIZE){
                    taskList.add(executor.submit(new LineProcessingTask(scanner.next())));
                    counter++;
                } else {
                    logger.info("-----------TASK BUFFER IS FULL-----------");
                    logger.info("-----------TASK LIST SIZE IS '{}", taskList.size());
                    logger.info("Dropping counter to 0...");
                    counter=0;
                    logger.info("writing results to '{}", resultFile);
                    for(Future<Pair<String, Integer>> future: taskList){
                        var result = future.get();
                        //logger.info("Processing result is '{}'", result);
                    }
                    logger.info("Clearing task list");
                    taskList.clear();
                }
                // TODO: NotImplemented: вычитываем CHUNK_SIZE строк для параллельной обработки

                // TODO: NotImplemented: обрабатывать строку с помощью LineProcessor. Каждый поток обрабатывает свою строку.

                // TODO: NotImplemented: добавить обработанные данные в результирующий файл
            }
            executor.shutdown();
        } catch (IOException exception) {
            logger.error("", exception);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("",e);
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        // TODO: NotImplemented: остановить поток writerThread
//        try {
//            fileWriterThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info("Finish main thread {}", Thread.currentThread().getName());
    }

    private void checkFileExists(@Nonnull File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("File '" + file + "' not exists");
        }
    }
}

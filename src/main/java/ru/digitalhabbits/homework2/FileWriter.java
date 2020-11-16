package ru.digitalhabbits.homework2;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Exchanger;

import static java.lang.Thread.currentThread;
import static org.slf4j.LoggerFactory.getLogger;

public class FileWriter implements Runnable {
    private static final Logger logger = getLogger(FileWriter.class);

    Exchanger<String> exchanger;
    File resultFile;

    public FileWriter(Exchanger<String> exchanger, File resultFile) {
        this.exchanger = exchanger;
        this.resultFile = resultFile;
    }

    @Override
    public void run() {
        logger.info("Started writer thread {}", currentThread().getName());
        try(java.io.FileWriter writer = new java.io.FileWriter(resultFile)) {

            writer.write(exchanger.exchange(new String()));
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Finish writer thread {}", currentThread().getName());
    }
}

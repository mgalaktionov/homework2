package ru.digitalhabbits.homework2;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import java.util.concurrent.Callable;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.Thread.currentThread;

public class LineProcessingTask implements Callable {

    private final Logger logger = getLogger(LineProcessingTask.class);
    private final String string;

    public LineProcessingTask(String string) {
        this.string = string;
    }

    @Override
    public Pair<String, Integer> call() throws Exception {
        logger.info("start processing in thread '{}", currentThread().getName());
        var processor = new LineCounterProcessor();
        return processor.process(string);
    }
}

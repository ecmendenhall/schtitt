package com.cmendenhall;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager implements Executor {
    private ExecutorService threads = Executors.newCachedThreadPool();

    public void execute(Runnable runnable) {
        threads.execute(runnable);
    }
}

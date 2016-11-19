package net.ethanpark.common.task;

import java.util.concurrent.Callable;

/**
 * Created by ethan on 2016/10/22.
 */
public class TaskFactory {

    /**
     * 自动生成一个Callable的Task并将其启动
     *
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> Task<T> startNew(Callable<T> callable) {
        Task<T> task = new Task(callable);
        task.start();
        return task;
    }

    /**
     * 自动生成一个Runnable的Task，并将其启动
     *
     * @param runnable
     * @return
     */
    public static Task startNew(Runnable runnable) {
        Task task = new Task(runnable);
        task.start();

        return task;
    }
}

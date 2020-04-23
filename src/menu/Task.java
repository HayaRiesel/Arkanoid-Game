package menu;

/**
 * The interface Task.
 *
 * @param <T> the type parameter of the task
 */
public interface Task<T> {
    /**
     * Run the task.
     *
     * @return the t
     */
    T run();
}

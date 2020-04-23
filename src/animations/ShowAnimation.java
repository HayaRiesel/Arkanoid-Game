package animations;

import menu.Task;

/**
 * The type Show animation.
 */
public class ShowAnimation {
    private Task<Void> t;


    /**
     * Make animation a task.
     *
     * @param runner    the runner
     * @param animation the animation
     * @return the task
     */
    public Task<Void> getTask(AnimationRunner runner, Animation animation) {

        this.t = new Task<Void>() {
            private AnimationRunner runnerTask = runner;
            private Animation animationTask = animation;

            public Void run() {
                this.runnerTask.run(this.animationTask);
                return null;
            }
        };
        return this.t;
    }
}

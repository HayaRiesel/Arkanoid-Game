package indicators;

/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Instantiates a new Counter.
     * add number to current count.
     *
     * @param count the count
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * Increase the counter.
     *
     * @param number the number that need to increase from the counter
     */
    public  void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease the counter.
     *
     * @param number the number that need to decrease from the counter
     */
// subtract number from current count.
    public void decrease(int number) {
        this.count--;
    }

    /**
     * get current count.
     *
     * @return the value of the counter
     */
    public int getValue() {
        return this.count;
    }
}
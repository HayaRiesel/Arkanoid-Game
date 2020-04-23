package listeners;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the hit listener that need to be add
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hit listener that need to be remove
     */
    void removeHitListener(HitListener hl);
}
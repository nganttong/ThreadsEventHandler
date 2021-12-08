import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    @Override
    public Map<String, Integer> tracker() {
        return tracker;
    }

    synchronized public void push(String message) {
        if(has(message)) {
            tracker.replace(message, tracker.get(message) + 1);
        } else {
            tracker.put(message, 1);
        }
    }

    synchronized public Boolean has(String message) {
        return this.tracker.containsKey(message)
                && (this.tracker.get(message) > 0);
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        if (has(message)) {
            tracker.replace(message, tracker.get(message) - 1);
        }
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}

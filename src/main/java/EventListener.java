public class EventListener extends Thread implements EventHandler {

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run() {
        while(!readyToQuit()) {
            if (shouldReply()) {
                reply();
            }
        }
    }

    public Boolean readyToQuit() {
        return this.eventTracker.has("quit");
    }

    public Boolean shouldReply() {
        return this.eventTracker.has(messageToListenFor);
    }

    public void reply() {
        this.eventTracker.handle(messageToListenFor, this);
    }

    @Override
    public void handle() {
        System.out.println(this.messageToReplyWith);
    }
}
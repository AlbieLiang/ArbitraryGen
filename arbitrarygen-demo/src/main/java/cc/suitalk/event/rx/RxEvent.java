package cc.suitalk.event.rx;

/**
 * Created by albieliang on 16/3/15.
 */
public class RxEvent {

    protected String action;
    protected Callback callback;

    public RxEvent() {

    }

    public RxEvent(String action) {
        this.action = action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Callback for the RxEvent by RxEventBus after the event was published.
     */
    public interface Callback {
        void onCallback(RxEvent event);
    }
}
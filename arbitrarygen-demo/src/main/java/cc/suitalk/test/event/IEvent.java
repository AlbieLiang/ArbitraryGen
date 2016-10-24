package cc.suitalk.test.event;

public class IEvent {

	protected String id;
	protected int order;
	protected ICallback callback;

	public IEvent() {
		;
	}

	public IEvent(ICallback callback) {
		this.callback = callback;
	}

	public interface ICallback {
		void onCallback(Object... args);
	}
}

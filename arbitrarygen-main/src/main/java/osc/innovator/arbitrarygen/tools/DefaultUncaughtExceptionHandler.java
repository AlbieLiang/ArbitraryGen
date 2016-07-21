package osc.innovator.arbitrarygen.tools;

import java.lang.Thread.UncaughtExceptionHandler;

import osc.innovator.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = "Crash.DefaultUncaughtExceptionHandler";


	public DefaultUncaughtExceptionHandler() {
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (thread == null || ex == null) {
			return;
		}
		Log.e(TAG, ex.toString());
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement traceElement : trace) {
			Log.e(TAG, "\tat " + traceElement);
		}
	}

}

package cc.suitalk.arbitrarygen.demo;

import cc.suitalk.arbitrarygen.extension.annotation.ArbitraryGenTask;
import cc.suitalk.arbitrarygen.extension.annotation.Keep;
import cc.suitalk.arbitrarygen.extension.annotation.RunInMainThread;
import cc.suitalk.arbitrarygen.extension.annotation.SourceLocation;
import cc.suitalk.arbitrarygen.extension.annotation.TargetLocation;
import cc.suitalk.arbitrarygen.extension.model.Command;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

@ArbitraryGenTask
public class MainActivity extends Activity {

	private static final String TAG = "AG.MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		Handler handler = new Handler();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		return true;
	}

	@SourceLocation(command = Command.Type.COMMAND_EXTRACT_VIEW_ID)
	protected int getResId() {
		return R.layout.activity_main;
	}
	
	@TargetLocation(command = Command.Type.COMMAND_EXTRACT_VIEW_ID)
	protected void autoGenGetViewByIdTargetMethod() {
//		findViewById(R.id.action_settings);
	}
	
	@Keep
	protected void testKeep() {
//		findViewById(R.id.action_settings);
	}
	
	@RunInMainThread
	protected void testRunInMainThread() {
		Log.i(TAG, "test run in main thread.");
	}
	
//	@RunInWorkerThread
//	protected void testRunInWorkerThread(@NotNull String name) {
//		Log.i(TAG, "test run in worker thread.");
//	}
}

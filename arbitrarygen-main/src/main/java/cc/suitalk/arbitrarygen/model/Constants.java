package cc.suitalk.arbitrarygen.model;


/**
 * 
 * @author AlbieLiang
 *
 */
public class Constants {

	/**
	 * Normal XML file format.
	 */
	public static final String SRC_FILE_FORMAT_SUFFIX_XML = "xml";
	/**
	 * Custom AG file format suffix, it means 'ArbitraryGen'.
	 */
	public static final String SRC_FILE_FORMAT_SUFFIX_AG = "ag";
	/**
	 * Custom rule file format suffix, this format file will record java file path as the task in it.
	 */
	public static final String SRC_FILE_FORMAT_SUFFIX_RULE = "rule";
	
	public static final String NEED_TO_HANDLE_TASK_ANNOTATION = "ArbitraryGenTask";// + ArbitraryGenTask.class.getSimpleName();
	public static final String NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION = "SourceLocation";// + SourceLocation.class.getSimpleName();
	public static final String NEED_TO_HANDLE_TARGET_LOCATION_ANNOTATION = "TargetLocation";// + TargetLocation.class.getSimpleName();
	public static final String NEED_TO_HANDLE_KEEP_ANNOTATION = "Keep";// + Keep.class.getSimpleName();
	public static final String NEED_TO_HANDLE_RUN_IN_MAIN_THREAD_ANNOTATION = "RunInMainThread";// + RunInMainThread.class.getSimpleName();
	public static final String NEED_TO_HANDLE_RUN_IN_WORKER_THREAD_ANNOTATION = "RunInWorkerThread";// + RunInWorkerThread.class.getSimpleName();

}

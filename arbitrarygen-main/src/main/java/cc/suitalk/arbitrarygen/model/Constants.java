package cc.suitalk.arbitrarygen.model;


/**
 * 
 * @author AlbieLiang
 *
 */
public interface Constants {

	/**
	 * Normal XML file format.
	 */
	String SRC_FILE_FORMAT_SUFFIX_XML = "xml";
	/**
	 * Custom AG file format suffix, it means 'ArbitraryGen'.
	 */
	String SRC_FILE_FORMAT_SUFFIX_AG = "ag";
	/**
	 * Custom rule file format suffix, this format file will record java file path as the task in it.
	 */
	String SRC_FILE_FORMAT_SUFFIX_RULE = "rule";
	
	String NEED_TO_HANDLE_TASK_ANNOTATION = "ArbitraryGenTask";// + ArbitraryGenTask.class.getSimpleName();
	String NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION = "SourceLocation";// + SourceLocation.class.getSimpleName();
	String NEED_TO_HANDLE_TARGET_LOCATION_ANNOTATION = "TargetLocation";// + TargetLocation.class.getSimpleName();
	String NEED_TO_HANDLE_KEEP_ANNOTATION = "Keep";// + Keep.class.getSimpleName();
	String NEED_TO_HANDLE_RUN_IN_MAIN_THREAD_ANNOTATION = "RunInMainThread";// + RunInMainThread.class.getSimpleName();
	String NEED_TO_HANDLE_RUN_IN_WORKER_THREAD_ANNOTATION = "RunInWorkerThread";// + RunInWorkerThread.class.getSimpleName();

}

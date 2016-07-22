package osc.innovator.arbitrarygen.extension;

public interface IDirector {

	String getFileSuffix();

	boolean useCustomizeParser();

	ICustomizeParser getCustomizeParser();

	ICustomizeConvertor getCustomizeAnalyzer();
}

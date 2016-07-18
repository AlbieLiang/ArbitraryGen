package osc.innovator.arbitrarygen.extension;

public interface IDirector {

	public String getFileSuffix();

	public boolean useCustomizeParser();

	public ICustomizeParser getCustomizeParser();

	public ICustomizeConvertor getCustomizeAnalyzer();
}

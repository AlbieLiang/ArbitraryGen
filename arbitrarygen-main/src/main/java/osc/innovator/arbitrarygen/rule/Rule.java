package osc.innovator.arbitrarygen.rule;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Rule {
	
	private Project mDefaultProject;
	private List<Project> mProjects;

	public Rule() {
		mProjects = new LinkedList<Project>();
	}
	
	public List<Project> getProjects() {
		return mProjects;
	}

	public void addProjects(List<Project> projects) {
		if (projects != null) {
			mProjects.addAll(projects);
		}
	}

	public boolean addProject(Project p) {
		if (p != null && !mProjects.contains(p)) {
			return mProjects.add(p);
		}
		return false;
	}
	
	public boolean removeProject(Project p) {
		return mProjects.remove(p);
	}
	
	public Project getDefaultProject() {
		return mDefaultProject;
	}

	public void setDefaultProject(Project p) {
		this.mDefaultProject = p;
	}
}

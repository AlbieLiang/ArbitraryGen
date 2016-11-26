package cc.suitalk.arbitrarygen.rule;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RuleFileObject {

	private String root;
	private String author;
	private String date;

	private List<Project> projectList;

	public RuleFileObject() {
		projectList = new LinkedList<>();
	}
	
	public List<Project> getProjects() {
		return projectList;
	}

	public void addProjects(List<Project> projects) {
		if (projects != null) {
			projectList.addAll(projects);
		}
	}

	public boolean addProject(Project p) {
		if (p != null && !projectList.contains(p)) {
			return projectList.add(p);
		}
		return false;
	}
	
	public boolean removeProject(Project p) {
		return projectList.remove(p);
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

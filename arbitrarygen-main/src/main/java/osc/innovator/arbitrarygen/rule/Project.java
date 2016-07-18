package osc.innovator.arbitrarygen.rule;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Project {

	private Rule mRule;
	private String mRoot;
	private String mName;
	private String mAuthor;
	private String mDate;
	private List<String> mFormats;
	private List<String> mSrcs;
	private List<String> mSrcDirs;
	private List<String> mSrcDirRecursions;
	private List<String> mSrcfiles;
	
	public Project() {
		mFormats = new LinkedList<String>();
		mSrcs = new LinkedList<String>();
		mSrcDirs = new LinkedList<String>();
		mSrcDirRecursions = new LinkedList<String>();
		mSrcfiles = new LinkedList<String>();
	}
	
	public Rule getRule() {
		return mRule;
	}

	public void setRule(Rule rule) {
		this.mRule = rule;
	}

	public String getRoot() {
		return mRoot;
	}
	
	public void setRoot(String root) {
		this.mRoot = root;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	
	public String getAuthor() {
		return mAuthor;
	}
	
	public void setAuthor(String author) {
		this.mAuthor = author;
	}
	
	public String getDate() {
		return mDate;
	}
	
	public void setDate(String date) {
		this.mDate = date;
	}
	
	public List<String> getFormats() {
		return mFormats;
	}
	
	public void addFormats(List<String> formats) {
		Util.addAll(mFormats, formats);
	}
	
	public boolean addFormat(String format) {
		return Util.add(mFormats, format);
	}
	
	public boolean removeFormat(String format) {
		return mFormats.remove(format);
	}
	
	public List<String> getSrcs() {
		return mSrcs;
	}
	
	public void addSrcs(List<String> srcs) {
		Util.addAll(mSrcs, srcs);
	}
	
	public boolean addSrc(String src) {
		return Util.add(mSrcs, src);
	}
	
	public boolean removeSrc(String src) {
		return mSrcs.remove(src);
	}
	
	
	public List<String> getSrcDirs() {
		return mSrcDirs;
	}
	
	public void addSrcDirs(List<String> srcDirs) {
		Util.addAll(mSrcDirs, srcDirs);
	}
	
	public boolean addSrcDir(String srcDir) {
		return Util.add(mSrcDirs, srcDir);
	}
	
	public boolean removeSrcDir(String srcDir) {
		return mSrcDirs.remove(srcDir);
	}
	
	public List<String> getSrcDirRecursions() {
		return mSrcDirRecursions;
	}
	
	public void addSrcDirRecursions(List<String> srcDirRecursions) {
		Util.addAll(mSrcDirRecursions, srcDirRecursions);
	}
	
	public boolean addSrcDirRecursion(String srcDirRecursion) {
		return Util.add(mSrcDirRecursions, srcDirRecursion);
	}
	
	public boolean removeSrcDirRecursion(String srcDirRecursion) {
		return mSrcDirRecursions.remove(srcDirRecursion);
	}
	
	public List<String> getSrcfiles() {
		return mSrcfiles;
	}
	
	public void addSrcfiles(List<String> srcfiles) {
		Util.addAll(mSrcfiles, srcfiles);
	}
	
	public boolean addSrcfile(String srcfile) {
		return Util.add(mSrcfiles, srcfile);
	}
	
	public boolean removeSrcfile(String srcfile) {
		return mSrcfiles.remove(srcfile);
	}
}

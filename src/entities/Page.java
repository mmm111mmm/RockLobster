package entities;

public class Page {

	private String filename;
	private String string;

	public Page(String filename, String page) {
		this.setFilename(filename);
		this.setString(page);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

}

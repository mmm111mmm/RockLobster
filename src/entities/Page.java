package entities;

public class Page implements ContentAndFilename {

	private String filename;
	private String content;

	public Page(String filename, String page) {
		this.setFilename(filename);
		this.setContent(page);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String string) {
		this.content = string;
	}

}

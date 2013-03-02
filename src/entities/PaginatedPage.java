package entities;

public class PaginatedPage implements ContentAndFilename {

	private String fileName;
	private int currentPage;
	private String content;
	private static final String PAGINATION_URL_LEADING_TEXT = ".";
	private static final String PAGINATION_URL_ENDING_TEXT = "";
	
	public PaginatedPage(String fileName, int currentPage, String content) {
		this.fileName = fileName;
		this.currentPage = currentPage;
		this.content = content;
	}

	public String getFilename() {
		return PaginatedPage.getGeneratedPaginationFilename(currentPage, this.fileName);
	}

	public void setFilename(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public static String getGeneratedPaginationFilename(int currentPage, String relativeFileName) {
		String urlPageNumText = "";
		if(currentPage!=1) {
			urlPageNumText = PAGINATION_URL_LEADING_TEXT + String.valueOf(currentPage-1) + PAGINATION_URL_ENDING_TEXT;
		}
		return relativeFileName.replaceFirst("\\.\\d+\\.pagination\\.template", urlPageNumText+".html");
	}
}


import java.util.List;

public class Page {
	
	private String url;
	private String html;
	private List<String> links;
	private List<String> linkNames;
	private String title;
	
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public List<String> getLinkNames() {
		return linkNames;
	}
	public void setLinkNames(List<String> linkNames) {
		this.linkNames = linkNames;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}


public class Open {
    String url;
    double timeout;

    Open(String url, double timeout) {
        this.url = url;
        this.timeout = timeout;
    }
    
    public String getUrl() {
        return this.url;
    }
}
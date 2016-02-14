package au.shamsutdinov.artur.parser.data;

/**
 * POJO class representing link information.
 */
public class Link {
    private String url;
    private String title;

    public Link() {
    }

    public Link(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Link) {
            Link link = (Link) o;
            return (title == null ? link.title == null : title.equals(link.title))
                    && (url == null ? link.url == null : url.equals(link.url));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (title == null ? 0 : title.hashCode());
        result = 31 * result + (url == null ? 0 : url.hashCode());
        return result;
    }
}

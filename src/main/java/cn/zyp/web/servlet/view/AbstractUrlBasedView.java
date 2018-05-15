package cn.zyp.web.servlet.view;

public  abstract class AbstractUrlBasedView extends AbstractView {
    private String url;

    public AbstractUrlBasedView(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

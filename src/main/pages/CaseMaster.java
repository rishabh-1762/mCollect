package pages;

public class CaseMaster {
    private Page page;

    public CaseMasterPage(Page page) {
        this.page = page;
    }

    private String apacColumn =
            "table tbody tr td:nth-child(9)";

    public String getFirstApacNumber() {
        page.waitForSelector(apacColumn);
        return page.locator(apacColumn).first().textContent().trim();
    }
}

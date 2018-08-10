package com.wwf.component.bean;

public class PersonalCenterBean {

    /**
     * code : 200
     * data : {"privacy_url":"http://ols.webi.com.cn/app/help/privacy_clause","download_url":"http://sj.qq.com/"}
     * msg :
     * timestamp : 1533114024862
     */

    /**
     * privacy_url : http://ols.webi.com.cn/app/help/privacy_clause
     * download_url : http://sj.qq.com/
     */

    private String privacy_url;
    private String download_url;

    public String getPrivacy_url() {
        return privacy_url;
    }

    public void setPrivacy_url(String privacy_url) {
        this.privacy_url = privacy_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

}

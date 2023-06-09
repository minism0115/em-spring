package em.capi.domain;

public class AuthOptions {

    private String userId;
    private String vid;

    public AuthOptions(String userId, String vid) {
        this.userId = userId;
        this.vid = vid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}

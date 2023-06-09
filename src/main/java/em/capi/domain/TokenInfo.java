package em.capi.domain;

import java.util.List;

public class TokenInfo {
    private List<TokenFlag> flags;
    private String message;

    public TokenInfo() {
    }

    public TokenInfo(Options options) {
        this.flags = options.getFlags();
        this.message = options.getMessage();
    }

    public List<TokenFlag> getFlags() {
        return flags;
    }

    public void setFlags(List<TokenFlag> flags) {
        this.flags = flags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

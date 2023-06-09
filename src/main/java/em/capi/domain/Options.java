package em.capi.domain;

import java.util.List;

public class Options {

    private List<TokenFlag> flags;
    private String message;

    public Options(List<TokenFlag> flags, String message) {
        this.flags = flags;
        this.message = message;
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

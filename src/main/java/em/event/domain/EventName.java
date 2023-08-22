package em.event.domain;

import lombok.Getter;

public enum EventName {

    REQUEST("REQUEST"),
    RESPONSE("RESPONSE");

    @Getter
    private final String value;

    EventName(String value){
        this.value = value;
    }
}

package em.event.domain;

import lombok.Getter;

public enum EventName {

    EXPORT_REQUESTED("EXPORT_REQUESTED");

    @Getter
    private final String value;

    EventName(String value){
        this.value = value;
    }
}

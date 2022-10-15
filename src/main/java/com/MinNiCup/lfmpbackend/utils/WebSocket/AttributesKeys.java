package com.MinNiCup.lfmpbackend.utils.WebSocket;

import com.MinNiCup.lfmpbackend.pojo.domain.Consult;
import com.MinNiCup.lfmpbackend.pojo.domain.User;
import lombok.Getter;

@Getter
public enum AttributesKeys {
    AC("AC", String.class),
    USER("USER", User.class),
    CONSULT("CONSULT", Consult.class);
    private String name;
    private Class type;

    AttributesKeys(String name, Class type) {
        this.name = name;
        this.type = type;
    }
}

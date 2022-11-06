package com.github.topisenpai.lavasrc.deezer;

import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;

public class DeezerException extends IllegalStateException {

    final Long errorCode;
    final String errorMessage;

    private DeezerException(String message, Long errorCode, String errorMessage) {
        super(message + ": (" + errorCode + ") " + errorMessage);

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    static DeezerException wrap(String message, JsonBrowser json) {
        var errorCode = json.get("data").index(0).get("errors").index(0).get("code").asLong(0);
        var errorMessage = json.get("data").index(0).get("errors").index(0).get("message").safeText();

        return new DeezerException(message, errorCode, errorMessage);
    }
}

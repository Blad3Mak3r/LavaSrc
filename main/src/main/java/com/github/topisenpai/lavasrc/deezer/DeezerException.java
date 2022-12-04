package com.github.topisenpai.lavasrc.deezer;

import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;

public class DeezerException extends IllegalStateException {

    final Long errorCode;
    final String errorMessage;

    final String errorType;

    private DeezerException(String message, Long errorCode, String errorMessage, String errorType) {
        super(message + ": (" + errorCode + ": " + errorType + ") " + errorMessage);

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    static DeezerException wrapFromPrivateAPI(String message, JsonBrowser json) {
        var errorCode = json.get("data").index(0).get("errors").index(0).get("code").asLong(0);
        var errorMessage = json.get("data").index(0).get("errors").index(0).get("message").safeText();
        var errorType = json.get("data").index(0).get("errors").index(0).get("type").safeText();

        return new DeezerException(message, errorCode, errorMessage, errorType);
    }

    static DeezerException wrapFromPublicAPI(String message, JsonBrowser json) {
        var errorCode = json.get("error").get("code").asLong(0);
        var errorMessage = json.get("error").get("message").safeText();
        var errorType = json.get("errors").get("type").safeText();

        return new DeezerException(message, errorCode, errorMessage, errorType);
    }
}

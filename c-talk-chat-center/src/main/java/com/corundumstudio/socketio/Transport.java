package com.corundumstudio.socketio;

import com.corundumstudio.socketio.transport.PollingTransport;
import com.corundumstudio.socketio.transport.WebSocketTransport;

/**
 * @className: Transport
 * @description: Transport - TODO
 * @version: v1.0.0
 * @author: haoduor
 */
public enum Transport {

    WEBSOCKET(WebSocketTransport.NAME),
    POLLING(PollingTransport.NAME);

    private final String value;

    Transport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.JAVA.Config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationWebSocketTest {

    @Test
    void canInstantiate() {
        NotificationWebSocket ws = new NotificationWebSocket();
        assertNotNull(ws);
    }
}

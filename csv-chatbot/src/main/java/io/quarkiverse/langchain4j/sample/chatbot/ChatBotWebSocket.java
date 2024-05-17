package io.quarkiverse.langchain4j.sample.chatbot;


import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

import jakarta.inject.Inject;


@WebSocket(path = "/chatbot")
public class ChatBotWebSocket {

    @Inject
    MovieMuse bot;

    @OnOpen
    public String onOpen() {
        return bot.chat("hello");
    }

    @OnTextMessage
    public String onMessage(String message) {
        return bot.chat(message);
    }

}

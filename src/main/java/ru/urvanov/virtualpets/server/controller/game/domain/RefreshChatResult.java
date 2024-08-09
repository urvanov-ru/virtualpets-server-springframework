package ru.urvanov.virtualpets.server.controller.game.domain;

import java.util.List;

public record RefreshChatResult(List<ChatMessage> chatMessages,
        Integer lastChatMessageId) {
};

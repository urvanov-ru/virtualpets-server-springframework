package ru.urvanov.virtualpets.server.api.domain;

import java.util.List;

public record RefreshChatResult(List<ChatMessage> chatMessages,
        Integer lastChatMessageId) {
};

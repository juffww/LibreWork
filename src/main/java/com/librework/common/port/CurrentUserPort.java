package com.librework.common.port;

import java.util.UUID;

public interface CurrentUserPort {
    UUID getCurrentUserId();
    String getCurrentUserName();
}

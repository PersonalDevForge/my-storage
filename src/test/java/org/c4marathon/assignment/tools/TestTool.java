package org.c4marathon.assignment.tools;

import org.c4marathon.assignment.file.domain.entity.File;
import org.c4marathon.assignment.share.domain.entity.Share;
import org.c4marathon.assignment.user.domain.entity.User;

public class TestTool {

    public static User createUser() {
        return User.of(1L, "test@example.com", "nickname", "password");
    }

    public static File createFile(User user) {
        return File.of(1L, user, null, "src/test/resources/uuid.jpg", "uuid","test.jpg", "jpg", 100L);
    }

}

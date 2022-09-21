import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ApplicationTest {
    @Test
    public void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    @Test
    public void testStringSplit() {
        String str = "Duplicate entry 'zhangsan' for key 'idx_username'";
        // java.lang.ArrayIndexOutOfBoundsException: 1000
        System.out.println(str.split(" ")[1000]);
    }
}

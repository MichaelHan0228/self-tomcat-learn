import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @Author hxd
 * @Date 2020/10/13 19:34
 * @Version 1.0
 * @since 1.0
 */
public class FileReaderTest {
    @Test
    public void testFileReaderPath() {
        boolean actual;
        try {
            // 已目前的掌握来看，server目录下的二级目录，均可直接访问，如.idea conf oa，且均不需要在开头加斜杠
            BufferedReader br = new BufferedReader(new FileReader(".idea/compiler.xml"));
            actual = true;
            String temp = null;
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);
            }
        } catch (Exception e) {
            actual = false;
        }
        Assert.assertEquals(true, actual);
    }
}

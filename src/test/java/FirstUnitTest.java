import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by ehuds on 2/2/2017.
 */
public class FirstUnitTest {
    @Test
    public void add_validNumbers_success() {
        // Arrange
        First first = new First();
        int a = 5;
        int b = 3;

        // Act
        int result = first.Add(a, b);

        // Assert
        assertEquals(8, result);
    }
}

package lucy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ParserTest {
    @Test
    public void testParseCommand_validCommand() {
        String input = "todo Buy groceries";
        String[] expected = {"todo", "Buy groceries"};
        String[] result = Parser.parseCommand(input);
        assertArrayEquals(expected, result);
    }
    @Test
    public void testParseCommand_invalidCommand() {
        String input = "mark";
        String[] expected = {"mark"};
        String[] result = Parser.parseCommand(input);
        assertArrayEquals(expected, result);
    }
}

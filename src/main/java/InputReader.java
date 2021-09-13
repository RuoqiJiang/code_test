import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InputReader {

    private static final String LINE_SEPARATOR = "\n";
    private static final String SPACE = " ";

    public List<InputItem> getInputItems(String input) {
        String[] lines = input.split(LINE_SEPARATOR);
        return Arrays.stream(lines)
                .map(this::getLine)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private InputItem getLine(String line) {
        String[] lines = line.split(SPACE);

        if (lines.length < 2) {
            return null;
        }

        return new InputItem().setItemName(lines[1]).setVolume(Integer.valueOf(lines[0]));
    }
}

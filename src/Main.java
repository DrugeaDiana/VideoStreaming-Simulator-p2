import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import inputclasses.InputData;
import tvclasses.TV;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        action(args[0], args[1]);
    }

    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final InputData inputData = objectMapper.readValue(new File(filePath1),
                InputData.class);

        final ArrayNode output = objectMapper.createArrayNode();
        final TV tv = new TV(inputData, output);
        tv.initialize();
        tv.mainLoop();

        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}

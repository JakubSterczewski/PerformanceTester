import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestResult {
    private long creationTime;
    private long addTime;
    private long searchTime;
    private long removeTime;
    private String message;

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getRemoveTime() {
        return removeTime;
    }

    public void setRemoveTime(long removeTime) {
        this.removeTime = removeTime;
    }

    public long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public void output(TestConfig testConfig) {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("Local time", LocalTime.now().withNano(0));
        map.put("Data type", testConfig.getDataType());
        map.put("Collection type", testConfig.getCollectionType());
        map.put("Test type", testConfig.getTestType());
        map.put("Creation time", this.getCreationTime());
        map.put("Add time", this.getAddTime());
        map.put("Search time", this.getSearchTime());
        map.put("Remove time", this.getRemoveTime());
        map.put("Optional message", this.getMessage());

        switch (testConfig.getResultOutput()) {
            case CLI -> map.forEach((key, val) -> System.out.println(key + ": " + val));
            case CSV -> {
                StringBuilder sb = new StringBuilder();

                int i = 0;
                for (String key : map.keySet()) {
                    sb.append(key);
                    if (++i < map.size()) {
                        sb.append(";");
                    }
                }

                sb.append("\n");

                i = 0;
                for (String key : map.keySet()) {
                    sb.append(map.get(key));
                    if (++i < map.size()) {
                        sb.append(";");
                    }
                }

                File file = new File("output.csv");
                try (Stream<String> lines = Files.lines(Paths.get(file.toString()))) {
                    lines
                            .skip(1)
                            .forEach(line -> sb.append("\n").append(line));
                } catch (IOException e) { }

                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
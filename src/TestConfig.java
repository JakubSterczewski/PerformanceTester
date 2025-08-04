import enums.collectionTypes;
import enums.dataTypes;
import enums.resultOutputs;
import enums.testTypes;

public class TestConfig {
    private final dataTypes dataType;
    private final int size;
    private final collectionTypes collectionType;
    private final testTypes testType;
    private final resultOutputs resultOutput;

    public TestConfig(dataTypes dataType, int size, collectionTypes collectionType, testTypes testType, resultOutputs resultOutput) {
        this.dataType = dataType;
        this.size = size;
        this.collectionType = collectionType;
        this.testType = testType;
        this.resultOutput = resultOutput;
    }

    public dataTypes getDataType() {
        return dataType;
    }

    public int getSize() {
        return size;
    }

    public collectionTypes getCollectionType() {
        return collectionType;
    }

    public testTypes getTestType() {
        return testType;
    }

    public resultOutputs getResultOutput() {
        return resultOutput;
    }
}
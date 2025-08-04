import enums.collectionTypes;
import enums.dataTypes;
import enums.resultOutputs;
import enums.testTypes;
import exceptions.IndexingNotSupportedException;
import testClasses.MyColor;
import testClasses.Person;
import testClasses.Point;
import testClasses.Product;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        dataTypes dataType = selectEnum(dataTypes.class, "Select data type:");
        int numElements = selectNumElements();
        collectionTypes collectionType = selectEnum(collectionTypes.class, "Select collection type:");
        testTypes testType = selectEnum(testTypes.class, "Select test type:");
        resultOutputs resultOutput = selectEnum(resultOutputs.class, "Select result outpur:");

        TestConfig testConfig = new TestConfig(dataType,
                numElements,
                collectionType,
                testType,
                resultOutput);

        long startTime, endTime;
        TestResult testResult = new TestResult();

        startTime = System.nanoTime();
        Collection<?> collection = Stream
                .generate(supplier(testConfig))
                .limit(testConfig.getSize())
                .collect(Collectors.toCollection(() -> switch (testConfig.getCollectionType()) {
                            case ARRAYLIST -> new ArrayList<>();
                            case LINKEDLIST -> new LinkedList<>();
                            case HASHSET -> new HashSet<>();
                            case TREESET -> new TreeSet<>();
                        }
                ));
        endTime = System.nanoTime();

        testResult.setCreationTime(endTime - startTime);

        switch (testConfig.getTestType()) {
            case READ_BY_INDEX -> {
                if (collection instanceof List<?> list) {
                    readByIndex(list, testResult);
                } else {
                    throw new IndexingNotSupportedException(collection.getClass().getName());
                }
            }
            case ADD_DELETE_ELEMENT -> {
                checkAddDeleteFrequency(collection, testResult, testConfig);
            }
            case SEARCH_ELEMENT -> {
                if (collection instanceof List<?> list) {
                    int rand = (int) (Math.random() * list.size());
                    var el = list.get(rand);

                    startTime = System.nanoTime();
                    for (var i : list) {
                        if (i.equals(el)) {
                            testResult.setMessage("Element has been found");
                        }
                    }
                    endTime = System.nanoTime();

                    testResult.setSearchTime(endTime - startTime);
                } else {
                    checkIsInCollection(collection, testResult);
                }
            }
            case CHECK_IS_IN_COLLECTION -> {
                checkIsInCollection(collection, testResult);
            }
        }

        testResult.output(testConfig);
    }

    //https://stackoverflow.com/questions/2205891/iterate-enum-values-using-java-generics
    public static <T extends Enum<T>> T selectEnum(Class<T> enumType, String message) {
        T selected = null;

        Map<Character, T> options = new HashMap<>();
        char i = 'a';
        for (T con : enumType.getEnumConstants()) {
            options.put(i, con);
            i++;
        }

        while (selected == null) {
            String input = printOptions(options, message);
            selected = getOptionsValue(options, input.toLowerCase());
        }
        return selected;
    }

    public static int selectNumElements() {
        Integer num = null;
        Map<Character, Integer> options = new HashMap<>();
        options.put('a', 100);
        options.put('b', 500);
        options.put('c', 1_000);
        options.put('d', 10_000);

        while (num == null) {
            String input = printOptions(options, "Select number of elements in collection or enter custom number:");

            try {
                if ((num = Integer.parseInt(input)) < 0) {
                    System.out.println("\nNumber of elements cannot be negative!");
                    num = null;
                }
            } catch (NumberFormatException e) {
                num = getOptionsValue(options, input);
            }
        }
        return num;
    }

    public static <T> String printOptions(Map<Character, T> options, String message) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        for (char s : options.keySet()) {
            System.out.println(s + " -> " + options.get(s));
        }
        System.out.print("Select: ");

        return scanner.nextLine();
    }

    private static <T> T getOptionsValue(Map<Character, T> options, String input) {
        char[] inputCharArray = input.toCharArray();
        if (inputCharArray.length == 1 && options.containsKey(inputCharArray[0])) {
            return options.get(inputCharArray[0]);
        }
        System.out.println("\nInvalid value, try again!");
        return null;
    }

    public static Supplier<?> supplier(TestConfig testConfig) {
        return switch (testConfig.getDataType()) {
            case INTEGER -> () -> new Random().nextInt();
            case DOUBLE -> () -> new Random().nextDouble();
            case PERSON -> () -> {
                int year = 1980 + (int) (Math.random() * 46);
                String name = "Name" + (int) (Math.random() * 1_000);
                return new Person(year, name);
            };
            case MYCOLOR -> () -> {
                int r = (int) (Math.random() * 256);
                int g = (int) (Math.random() * 256);
                int b = (int) (Math.random() * 256);
                return new MyColor(r, g, b);
            };
            case POINT -> () -> {
                double x = Math.random() * 2_001 - 1_000;
                double y = Math.random() * 2_001 - 1_000;
                return new Point(x, y);
            };
            case PRODUCT -> () -> {
                String name = "Product" + (int) (Math.random() * 1_000);
                double price = Math.random() * 10_000;
                return new Product(name, price);
            };
        };
    }

    public static <T> void readByIndex(List<T> list, TestResult result) {
        int size = list.size();
        long startTime = System.nanoTime();
        for (int i = 0; i < size / 10; i++) {
            list.get((int) (Math.random() * size));
        }
        long endTime = System.nanoTime();
        result.setSearchTime(endTime - startTime);
    }

    public static <T> void checkAddDeleteFrequency(Collection<T> collection, TestResult result, TestConfig testConfig) {
        int toAdd = Integer.MAX_VALUE - collection.size();
        if (toAdd >= collection.size() / 10) {
            toAdd = collection.size() / 10;
        }

        List<T> elementsToAdd = Stream
                .generate((Supplier<T>) supplier(testConfig))
                .limit(toAdd)
                .toList();

        long startTime = System.nanoTime();
        collection.addAll(elementsToAdd);
        long endTime = System.nanoTime();
        result.setAddTime(endTime - startTime);

        List<T> list = new ArrayList<>(collection);
        List<T> elementsToDelete = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            int rand = (int) (Math.random() * list.size());
            elementsToDelete.add(list.get(rand));
        }

        startTime = System.nanoTime();
        for (T el : elementsToDelete) {
            collection.remove(el);
        }
        endTime = System.nanoTime();
        result.setRemoveTime(endTime - startTime);
    }

    public static <T> void checkIsInCollection(Collection<T> collection, TestResult result) {
        int rand = (int) (Math.random() * collection.size());
        Object el = collection.toArray()[rand];

        long startTime = System.nanoTime();
        if (collection.contains(el)) {
            result.setMessage("Element has been found");
        } else {
            result.setMessage("Element hasn't been found");
        }
        long endTime = System.nanoTime();

        result.setSearchTime(endTime - startTime);
    }
}
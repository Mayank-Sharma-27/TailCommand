import java.util.List;

public interface Tail {

    List<String> readlines(String filePath, int n);

    default void follow(String filePath, int n) {
        throw new IllegalArgumentException("Not implemented yet");
    }

}

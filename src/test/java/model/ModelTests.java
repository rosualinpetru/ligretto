package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelTests {
    @Test
    public void streamTests() {
        var s = Stream.of(1,2,3,4);
        var s1 = s.takeWhile(it -> it < 2);
        System.out.println(s1.collect(Collectors.toList()));
        System.out.println(s.collect(Collectors.toList()));
        Assertions.assertTrue(true);
    }
}

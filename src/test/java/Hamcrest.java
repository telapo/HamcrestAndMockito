import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.davide.custom.IsNotNumber.notANumber;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class Hamcrest {

    private List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

    @Test
    @DisplayName("isA")
    public void instances() {
        assertThat(1L, isA(Long.class));
    }

    @Test
    @DisplayName("anyOf allOf")
    void anyOfAllOf() {
        assertThat("string", anyOf(is("str"), containsString("str")));
        assertThat(12, allOf(is(6 * 2), lessThan(20)));
    }

    @Test
    @DisplayName("lessThan")
    void lessThanTest() {

        assertThat("abc", is(lessThan("def")));

        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);

        assertThat(now, is(lessThan(tomorrow)));
    }

    @Test
    @DisplayName("greaterThanOrEqualTo closeTo")
    void numericals() {
        assertThat(12, greaterThanOrEqualTo(10));
        assertThat(12, lessThan(15));
        assertThat(3.14159, closeTo(Math.PI, 0.0001));
    }

    @Test
    @DisplayName("strings")
    void stringTests() {
        String s1 = "abc";
        String s2 = " abc     ";
        assertThat(s1, equalToIgnoringWhiteSpace(s2));

        String s3 = "this is a string";
        String[] split = s3.split(" ");
        Arrays.stream(split)
                .forEach(word -> assertThat(s3, containsString(word)));
    }

    @Test
    @DisplayName("collections")
    void collectionTest(){
        assertThat(strings, hasSize(6));
        assertThat(strings, containsInAnyOrder("a", "is", "list", "of", "this", "strings"));
        // every item contains either a or i
        assertThat(strings, everyItem(anyOf(containsString("a"),
                                            containsString("i"),
                                            containsString("o"))));
        assertThat(strings, hasItem("strings"));
    }

    @Test
    @DisplayName("arrays")
    void arraysTest(){
        String[] stringArray = "this is an array".split(" ");
        assertThat(stringArray, hasItemInArray("array"));
        assertThat(stringArray, arrayContainingInAnyOrder("an", "this", "array", "is"));
    }

    @Test
    @DisplayName("objects")
    void objectTest(){
        Person person= null;
        assertThat(person, nullValue());
        person = new Person("Gianni");
        Person copy = new Person("Gianni");
        assertThat(copy, not(sameInstance(person)));
        assertThat(copy, is(equalTo(person)));
    }

    @Test
    @DisplayName("custom matcher")
    void customMatcher(){
        assertThat(Math.sqrt(-1), is(notANumber()));
    }
}

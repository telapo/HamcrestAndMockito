import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MockitoTest {

    @Mock
    private List<String> mockedList;

    @Test
    @DisplayName("mock base")
    void mockingBase() {
        mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    @DisplayName("stubbing base")
    void stubbingBase() {
        List<String> mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        assertAll("stubbing",
                () -> assertEquals("first", mockedList.get(0))
        );
        try {
            mockedList.get(1);
        } catch (RuntimeException ex) {
        }
    }


    @Test
    @DisplayName("argumentsMatcher")
    void argMatcherTest() {
        List<String> mockedList = mock(List.class);
        when(mockedList.add(anyString())).thenReturn(true);

        mockedList.add("abcdef");

        // check that method called with an arg that is greater than 5 chars
        verify(mockedList).add(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String s) {
                return s.length() > 5;
            }
        }));
    }

    @Test
    @DisplayName("argumentsMatcher lambda")
    void argMatcherLambdaTest() {
        List<String> mockedList = mock(List.class);
        when(mockedList.add(anyString())).thenReturn(true);

        mockedList.add("abcdef");

        // check that method called with an arg that is greater than 5 chars
        verify(mockedList).add(argThat(someString -> someString.length() > 5));
    }

}

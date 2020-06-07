import com.company.Movie;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class TestUnit {
    @Test
    public void testAdd() throws IOException {

        String film = "Блэйд фильм";
        double rating = Movie.getData(film,0);
        assertNull(rating);
    }
}

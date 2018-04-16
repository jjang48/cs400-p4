import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test class @see GraphProcessor
 *
 * @author Bryan Jin (bjin23@wisc.edu), Joon Jang (jjang48@wisc.edu), Aanjanaye Kajaria
 *         (kajaria@wisc.edu)
 */
public class GraphProcessorTest {

    private GraphProcessor gp;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        gp = new GraphProcessor();
    }

    @After
    public void tearDown() throws Exception {
        gp = null;
    }

    @Test
    public final void populateGraphShouldReturnNumberOfVerticesAdded() {
        int numVertices = gp.populateGraph("TestFile.txt");
        assertEquals("number of vertices added", 441, numVertices);
    }

}

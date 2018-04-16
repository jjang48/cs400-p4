import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
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
        assertEquals("number of vertices added", 427, numVertices);
    }

    @Test
    public final void getShortestPathOnAdjacentVertices() {
        gp.populateGraph("TestFile.txt");
        List<String> path = gp.getShortestPath("bullies", "bellies");
        List<String> correctPath = new ArrayList<String>();
        correctPath.add("bullies");
        correctPath.add("bellies");
        assertEquals("path between bullies and bellies:", correctPath, path);
    }

    @Test
    public final void getShortestDistanceOnAdjacentVertices() {
        gp.populateGraph("TestFile.txt");
        Integer distance = gp.getShortestDistance("bullies", "bellies");
        assertEquals("path between bullies and bellies:", new Integer(1), distance);
    }
    
     @Test
    public final void getShortestPathOnVertices()
    {
    		gp.populateGraph("TestFile.txt");
    		List<String> path = gp.getShortestPath("comedo", "charge");
    		List<String> correctPath = new ArrayList<String>();
    		correctPath.add("comedo");
    		correctPath.add("charge");
    		assertEquals("path between comedo and charge: ", correctPath, path);
    }
    
    @Test
    public final void getShortestDistanceOnVertices()
    {
    	gp.populateGraph("TestFile.txt");
    	Integer distance = gp.getShortestDistance("comedo", "charge");
    	assertEquals("path between comedo and charge:", new Integer(49), distance);
    }
    
    @Test
    public final void getDistanceOnItself()
    {
    		gp.populateGraph("TestFile.txt");
    		Integer distance = gp.getShortestDistance("bath", "bath");
    		assertEquals("path between bath and bath:", new Integer(-1), distance);
    }
}

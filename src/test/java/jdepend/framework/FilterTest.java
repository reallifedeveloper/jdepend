package jdepend.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class FilterTest extends JDependTestCase {

    public FilterTest() {
        super("FilterTest");
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        System.setProperty("user.home", getTestDataDir());
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    public void testDefault() {
        PackageFilter filter = new PackageFilter();
        assertEquals(5, filter.getFilters().size());
        assertFiltersExist(filter);
    }

    @Test
    public void testFile() throws IOException {

        String filterFile = getTestDataDir() + "jdepend.properties";

        PackageFilter filter = new PackageFilter(new File(filterFile));
        assertEquals(5, filter.getFilters().size());
        assertFiltersExist(filter);
    }

    @Test
    public void testCollection() throws IOException {

        Collection filters = new ArrayList();
        filters.add("java.*");
        filters.add("javax.*");
        filters.add("sun.*");
        filters.add("com.sun.*");
        filters.add("com.xyz.tests.*");

        PackageFilter filter = new PackageFilter(filters);
        assertEquals(5, filter.getFilters().size());
        assertFiltersExist(filter);
    }

    @Test
    public void testCollectionSubset() {
        Collection filters = new ArrayList();
        filters.add("com.xyz");
        PackageFilter filter = new PackageFilter(filters);
        assertEquals(1, filter.getFilters().size());
    }

    private void assertFiltersExist(PackageFilter filter) {
        assertFalse(filter.accept("java.lang"));
        assertFalse(filter.accept("javax.ejb"));
        assertTrue(filter.accept("com.xyz.tests"));
        assertFalse(filter.accept("com.xyz.tests.a"));
        assertTrue(filter.accept("com.xyz.ejb"));
    }
}
package jdepend.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class PropertyConfiguratorTest extends JDependTestCase {

    public PropertyConfiguratorTest() {
        super("PropertyConfiguratorTest");
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
    public void testDefaultFilters() {
        PropertyConfigurator c = new PropertyConfigurator();
        assertFiltersExist(c.getFilteredPackages());
        assertFalse(c.getAnalyzeInnerClasses());
    }

    @Test
    public void testFiltersFromFile() throws IOException {

        String file = getTestDataDir() + "jdepend.properties";

        PropertyConfigurator c = new PropertyConfigurator(new File(file));

        assertFiltersExist(c.getFilteredPackages());
        assertFalse(c.getAnalyzeInnerClasses());
    }

    private void assertFiltersExist(Collection filters) {
        assertEquals(5, filters.size());
        assertTrue(filters.contains("java.*"));
        assertTrue(filters.contains("javax.*"));
        assertTrue(filters.contains("sun.*"));
        assertTrue(filters.contains("com.sun.*"));
        assertTrue(filters.contains("com.xyz.tests.*"));
    }

    @Test
    public void testDefaultPackages() throws IOException {
        JDepend j = new JDepend();

        JavaPackage pkg = j.getPackage("com.xyz.a.neverchanges");
        assertNotNull(pkg);
        assertEquals(0, pkg.getVolatility());

        pkg = j.getPackage("com.xyz.b.neverchanges");
        assertNotNull(pkg);
        assertEquals(0, pkg.getVolatility());

        pkg = j.getPackage("com.xyz.c.neverchanges");
        assertNull(pkg);
    }
}
package jdepend.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

public class JarFileParserTest extends JDependTestCase {

    private File jarFile;
    private File zipFile;

    public JarFileParserTest() {
        super("JarFileParserTest");
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();

        jarFile = new File(getTestDataDir() + "test.jar");
        zipFile = new File(getTestDataDir() + "test.zip");
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    public void testInvalidJarFile() throws IOException {

        JavaClassBuilder builder = new JavaClassBuilder();
        File bogusFile = new File(getTestDataDir() + "bogus.jar");

        try {

            builder.buildClasses(bogusFile);
            fail("Should raise IOException");

        } catch (IOException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void testInvalidZipFile() throws IOException {

        JavaClassBuilder builder = new JavaClassBuilder();
        File bogusFile = new File(getTestDataDir() + "bogus.zip");

        try {

            builder.buildClasses(bogusFile);
            fail("Should raise IOException");

        } catch (IOException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void testJarFile() throws IOException {

        JavaClassBuilder builder = new JavaClassBuilder();

        Collection classes = builder.buildClasses(jarFile);
        assertEquals(5, classes.size());

        assertClassesExist(classes);
        assertInnerClassesExist(classes);
    }

    @Test
    public void testJarFileWithoutInnerClasses() throws IOException {

        FileManager fm = new FileManager();
        fm.acceptInnerClasses(false);

        JavaClassBuilder builder = new JavaClassBuilder(fm);

        Collection classes = builder.buildClasses(jarFile);
        assertEquals(4, classes.size());

        assertClassesExist(classes);
    }

    @Test
    public void testZipFile() throws IOException {

        JavaClassBuilder builder = new JavaClassBuilder();

        Collection classes = builder.buildClasses(zipFile);
        assertEquals(5, classes.size());

        assertClassesExist(classes);
        assertInnerClassesExist(classes);
    }

    @Test
    public void testZipFileWithoutInnerClasses() throws IOException {

        FileManager fm = new FileManager();
        fm.acceptInnerClasses(false);

        JavaClassBuilder builder = new JavaClassBuilder(fm);

        Collection classes = builder.buildClasses(zipFile);
        assertEquals(4, classes.size());

        assertClassesExist(classes);
    }

    @Test
    public void testCountClasses() throws IOException {

        JDepend jdepend = new JDepend();
        jdepend.addDirectory(getTestDataDir());

        jdepend.analyzeInnerClasses(true);
        assertEquals(10, jdepend.countClasses());

        jdepend.analyzeInnerClasses(false);
        assertEquals(8, jdepend.countClasses());
    }

    private void assertClassesExist(Collection classes) {
        assertTrue(classes.contains(new JavaClass(
                "jdepend.framework.ExampleAbstractClass")));
        assertTrue(classes.contains(new JavaClass(
                "jdepend.framework.ExampleInterface")));
        assertTrue(classes.contains(new JavaClass(
                "jdepend.framework.ExampleConcreteClass")));
    }

    private void assertInnerClassesExist(Collection classes) {
        assertTrue(classes.contains(new JavaClass(
                "jdepend.framework.ExampleConcreteClass$ExampleInnerClass")));
    }
}
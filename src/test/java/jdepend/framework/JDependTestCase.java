package jdepend.framework;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public abstract class JDependTestCase {

    private String homeDir;
    private String testDir;
    private String testDataDir;
    private String buildDir;
    private String testBuildDir;
    private String packageSubDir;
    private String originalUserHome;

    public JDependTestCase(String name) {
    }

    protected void setUp() {

        System.setProperty("jdepend.home", ".");

        homeDir = System.getProperty("jdepend.home");
        if (homeDir == null) {
            fail("Property 'jdepend.home' not defined");
        }
        // homeDir = homeDir + File.separator;
        // testDir = homeDir + File.separator + "test" + File.separator;
        // testDataDir = testDir + "data" + File.separator;
        // buildDir = homeDir + "build" + File.separator;
        packageSubDir = "jdepend" + File.separator + "framework" + File.separator;
        originalUserHome = System.getProperty("user.home");

        testDir = "src/test/java/";
        testDataDir = "src/test/resources/data/";
        buildDir = "target/classes/";
        testBuildDir = "target/test-classes/";
    }

    protected void tearDown() {
        System.setProperty("user.home", originalUserHome);
    }

    public String getHomeDir() {
        return homeDir;
    }

    public String getTestDataDir() {
        return testDataDir;
    }

    public String getTestDir() {
        return testDir;
    }

    public String getBuildDir() {
        return buildDir;
    }

    public String getTestBuildDir() {
        return testBuildDir;
    }

    public String getPackageSubDir() {
        return packageSubDir;
    }
}
package jdepend.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The <code>ExampleTest</code> is an example <code>TestCase</code> that demonstrates tests for measuring the distance from the main
 * sequence (D), package dependency constraints, and the existence of cyclic package dependencies.
 * <p>
 * This test analyzes the JDepend class files.
 *
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class ExampleTest {

    private JDepend jdepend;

    public String jdependHomeDirectory;

    @BeforeEach
    protected void setUp() throws IOException {
        PackageFilter filter = new PackageFilter();
        filter.addPackage("java.*");
        filter.addPackage("javax.*");
        jdepend = new JDepend(filter);

        // String classesDir =
        // jdependHomeDirectory + File.separator + "build";

        // jdepend.addDirectory(classesDir);
        jdepend.addDirectory("target/classes");
        jdepend.addDirectory("target/test-classes");
    }

    /**
     * Tests the conformance of a single package to a distance from the main sequence (D) within a tolerance.
     */
    @Test
    public void testOnePackageDistance() {

        double ideal = 0.0;
        double tolerance = 0.8;

        jdepend.analyze();

        JavaPackage p = jdepend.getPackage("jdepend.framework");

        assertEquals(ideal, p.distance(), tolerance, "Distance exceeded: " + p.getName());
    }

    /**
     * Tests that a single package does not contain any package dependency cycles.
     */
    @Test
    public void testOnePackageHasNoCycles() {

        jdepend.analyze();

        JavaPackage p = jdepend.getPackage("jdepend.framework");

        assertEquals(false, p.containsCycle(), "Cycles exist: " + p.getName());
    }

    /**
     * Tests the conformance of all analyzed packages to a distance from the main sequence (D) within a tolerance.
     */
    @Test
    public void testAllPackagesDistance() {

        double ideal = 0.0;
        double tolerance = 1.0;

        Collection packages = jdepend.analyze();

        for (Iterator iter = packages.iterator(); iter.hasNext();) {
            JavaPackage p = (JavaPackage) iter.next();
            assertEquals(ideal, p.distance(), tolerance, "Distance exceeded: " + p.getName());
        }
    }

    /**
     * Tests that a package dependency cycle does not exist for any of the analyzed packages.
     */
    @Test
    public void testAllPackagesHaveNoCycles() {

        Collection packages = jdepend.analyze();

        assertEquals(false, jdepend.containsCycles(), "Cycles exist");
    }

    /**
     * Tests that a package dependency constraint is matched for the analyzed packages.
     * <p>
     * Fails if any package dependency other than those declared in the dependency constraints are detected.
     */
    @Test
    public void testDependencyConstraint() {

        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage framework = constraint.addPackage("jdepend.framework");
        JavaPackage text = constraint.addPackage("jdepend.textui");
        JavaPackage xml = constraint.addPackage("jdepend.xmlui");
        JavaPackage swing = constraint.addPackage("jdepend.swingui");
        JavaPackage jdependframeworkp2 = constraint.addPackage("jdepend.framework.p2");
        JavaPackage jdependframeworkp3 = constraint.addPackage("jdepend.framework.p3");
        JavaPackage jdependframeworkp1 = constraint.addPackage("jdepend.framework.p1");
        JavaPackage orgjunitjupiterapi = constraint.addPackage("org.junit.jupiter.api");

        text.dependsUpon(framework);
        xml.dependsUpon(framework);
        xml.dependsUpon(text);
        swing.dependsUpon(framework);
        framework.dependsUpon(jdependframeworkp2);
        framework.dependsUpon(jdependframeworkp3);
        framework.dependsUpon(jdependframeworkp1);
        framework.dependsUpon(orgjunitjupiterapi);

        jdepend.analyze();

        assertEquals(true, jdepend.dependencyMatch(constraint), "Constraint mismatch");
    }

}
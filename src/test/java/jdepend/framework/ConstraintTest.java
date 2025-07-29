package jdepend.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class ConstraintTest extends JDependTestCase {

    private JDepend jdepend;

    public ConstraintTest() {
        super("ConstraintTest");
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        PackageFilter filter = new PackageFilter();
        filter.addPackage("java.*");
        filter.addPackage("javax.*");
        jdepend = new JDepend(filter);
    }

    @Test
    public void testMatchPass() {

        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage expectedA = constraint.addPackage("A");
        JavaPackage expectedB = constraint.addPackage("B");

        expectedA.dependsUpon(expectedB);

        JavaPackage actualA = new JavaPackage("A");
        JavaPackage actualB = new JavaPackage("B");

        actualA.dependsUpon(actualB);

        jdepend.addPackage(actualA);
        jdepend.addPackage(actualB);

        assertEquals(true, jdepend.dependencyMatch(constraint));
    }

    @Test
    public void testMatchFail() {

        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage expectedA = constraint.addPackage("A");
        JavaPackage expectedB = constraint.addPackage("B");
        JavaPackage expectedC = constraint.addPackage("C");

        expectedA.dependsUpon(expectedB);

        JavaPackage actualA = new JavaPackage("A");
        JavaPackage actualB = new JavaPackage("B");
        JavaPackage actualC = new JavaPackage("C");

        actualA.dependsUpon(actualB);
        actualA.dependsUpon(actualC);

        jdepend.addPackage(actualA);
        jdepend.addPackage(actualB);
        jdepend.addPackage(actualC);

        assertEquals(false, jdepend.dependencyMatch(constraint));
    }

    @Test
    public void testJDependConstraints() throws IOException {

        jdepend.addDirectory(getBuildDir());
        jdepend.addDirectory(getTestBuildDir());

        jdepend.analyze();

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
        xml.dependsUpon(framework);
        framework.dependsUpon(jdependframeworkp2);
        framework.dependsUpon(jdependframeworkp3);
        framework.dependsUpon(jdependframeworkp1);
        framework.dependsUpon(orgjunitjupiterapi);

        assertEquals(true, jdepend.dependencyMatch(constraint));
    }
}
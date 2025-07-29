package jdepend.framework;

import java.math.BigDecimal;

import javax.crypto.Cipher;

import org.junit.jupiter.api.Disabled;

import jdepend.framework.p1.ExampleInnerAnnotation;
import jdepend.framework.p2.ExampleEnum;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class ExampleConcreteClass extends ExampleAbstractClass {

    private java.sql.Statement[] statements;

    public ExampleConcreteClass() {
    }

    public void a() {
        try {
            java.net.URL url = new java.net.URL("http://www.clarkware.com");
        } catch (Exception e) {
        }
    }

    public java.util.Vector b(String[] s, java.text.NumberFormat nf) {
        return null;
    }

    public void c(BigDecimal bd, byte[] bytes) throws java.rmi.RemoteException {
        int[] a = { 1, 2, 3 };
        int[][] b = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
    }

    public java.io.File[] d() throws java.io.IOException {
        java.util.jar.JarFile[] files = new java.util.jar.JarFile[1];
        return new java.io.File[10];
    }

    public java.lang.String[] e() {
        java.lang.String[] strings = new java.lang.String[1];
        return strings;
    }

    @ExampleAnnotation(c1 = java.awt.geom.AffineTransform.class, c2 = java.awt.image.renderable.ContextualRenderedImageFactory.class, c3 = @ExampleInnerAnnotation({
            java.awt.im.InputContext.class, java.awt.dnd.peer.DragSourceContextPeer.class }), c4 = ExampleEnum.E1)
    @Disabled
    public void f() throws Exception {
        javax.crypto.Cipher cipher = Cipher.getInstance("foo");
    }

    public class ExampleInnerClass {
    }
}

class ExamplePackageClass {
}
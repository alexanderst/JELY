package jely.test.io;

import jely.Ecg;
import jely.io.EdfEcgFile;
import org.junit.Test;

public class EdfEcgFileTest {
    @Test
    public void testEdfLoading() {
        Ecg ecg = new EdfEcgFile("src/test/resources/stub.edf");
    }
}

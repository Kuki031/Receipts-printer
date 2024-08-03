package org.oop;

import java.io.FileWriter;
import java.io.IOException;

public interface Writeable {
    String writeReceiptHead();
    String writeReceiptBody();
    String writeReceiptFooter();
}

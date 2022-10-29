package org.openjfx.userinterface;

import org.openjfx.project.Project;

public interface Reader {
    Project readNextProject();
    void reset();
    boolean hasNext();
}
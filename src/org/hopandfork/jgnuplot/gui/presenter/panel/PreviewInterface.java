package org.hopandfork.jgnuplot.gui.presenter.panel;

import java.awt.*;
import java.awt.event.ComponentListener;

public interface PreviewInterface {

    void addComponentListener (ComponentListener listener);
    void displayMessage (String message);
    void displayImage (Image image);
    void clear();

    Dimension getSize();
}

/*
 * Copyright 2006, 2017 Maximilian H Fabricius, Hop and Fork.
 * 
 * This file is part of JGNUplot.
 * 
 * JGNUplot is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * JGNUplot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JGNUplot.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hopandfork.jgnuplot.gui.utility;

import javax.swing.*;
import java.awt.*;

public class JOptionPaneUtils {


    public static void showWarning(Component owner, String title, String message) {
        JOptionPane.showMessageDialog(owner,
                message,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(Component owner, String title, String message) {
        JOptionPane.showMessageDialog(owner,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
}

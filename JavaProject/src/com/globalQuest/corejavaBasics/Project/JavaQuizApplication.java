package com.globalQuest.corejavaBasics.Project;

import javax.swing.SwingUtilities;

public class JavaQuizApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new JavaQuizGUI();
        });
    }
}
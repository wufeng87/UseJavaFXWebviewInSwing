import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by delll on 2017/3/17.
 */
public class Main {
    private static Browser browser;

    private static void initAndShowGUI() {
        // This method is invoked on Swing thread
        JFrame frame = new JFrame("FX");
        JPanel mainframe=new JPanel();

        JButton button = new JButton("refresh");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (browser != null) {
                    browser.executeScript("refresh", new Date().toString());
                }
            }
        });

        mainframe.add(button);

        final JFXPanel fxPanel = new JFXPanel();
        mainframe.add(fxPanel);

        frame.setBounds(100, 100, 800, 800);
        frame.getContentPane().add(mainframe);
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private static void initFX(JFXPanel fxPanel) {
        String url = Main.class.getResource("./html/index.html")
                .toExternalForm();
        // This method is invoked on JavaFX thread
        browser = new Browser(url);
        Scene scene = scene = new Scene(browser,750,500, Color.web("#666970"));
        fxPanel.setScene(scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}

package game.battlefieldbysocket.D_frameworksAndDrivers.ui;

import game.battlefieldbysocket.C_adapters.AdapterOutputPort;
import game.battlefieldbysocket.C_adapters.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingView implements AdapterOutputPort {

    private Controller controller;
    private final int fieldSideLength = 100;
    private final JPanel grid = new JPanel();
    private final JTextArea logArea = new JTextArea(); // Log-Bereich

    public SwingView() {
        logArea.setEditable(false);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void createAndShowGUI() {
        int n = controller.getSideLengthFromGameConf();

        JFrame frame = new JFrame("Battle Field");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(n * fieldSideLength, n * fieldSideLength + 100);

        grid.setLayout(new GridLayout(n, n));
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                JPanel field = createClickableField(col, row);
                grid.add(field);
            }
        }

        logArea.setPreferredSize(new Dimension(frame.getWidth(), 100));

        frame.setLayout(new BorderLayout());
        frame.add(grid, BorderLayout.CENTER);
        frame.add(new JScrollPane(logArea), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createClickableField(int x, int y) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(fieldSideLength, fieldSideLength));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBackground(Color.LIGHT_GRAY);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = new Point(x, y);
                controller.handleFieldClick(point);
            }
        });

        return panel;
    }

    @Override
    public void setFieldLabel(Point point, String label) {
        logArea.setText(label);
    }

    @Override
    public void presentQuestionWhereToShoot() {
        // Optional
    }

    public Point setOpenForInputAndGetIt() {
        return null;
    }
}

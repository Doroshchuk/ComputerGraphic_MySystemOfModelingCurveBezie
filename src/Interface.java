import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Float.parseFloat;

public class Interface {
    private JPanel firstInputPanel, firstDrawingPanel;
    private JPanel secondInputPanel, secondDrawingPanel;
    private JPanel thirdInputPanel, thirdDrawingPanel;
    private JPanel fourthInputPanel, fourthDrawingPanel;
    private JPanel buttonsPanel;
    private JTabbedPane tabbedPane;
    private JFrame frame;
    private Plot elementaryCurvePlot, sharkPlot, gridPlot, surfacePlot;
    private Font font = new Font("TimesRoman", Font.PLAIN, 10);
    private String firstTabName = "Кривая";
    private String secondTabName = "Контур";
    private String thirdTabName = "Сетка";
    private String fourthTabName = "Поверхность";

    public static void main(String[] args) {
        new Interface();
    }

    private Interface() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                frame = new JFrame("This elementaryCurvePlot is created by Daria Doroshchuk");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setResizable(false);
                frame.setSize(1200, 720);

                tabbedPane = new JTabbedPane();
                tabbedPane.setFont(font);

                JPanel content = new JPanel();
                content.setLayout(new BorderLayout());

                buttonsPanel = new JPanel(null);
                content.add(buttonsPanel, BorderLayout.NORTH);
                buttonsPanel.setPreferredSize(new Dimension(frame.getWidth(), 30));

                JPanel firstMainPanel = new JPanel();
                firstInputPanel = new JPanel();
                firstDrawingPanel = new JPanel();
                setJPanels(firstInputPanel, firstDrawingPanel);
                elementaryCurvePlot = new Plot();
                setUpInitPoints(elementaryCurvePlot, 10, 230, 25, 40, 50, 150, 200, 170, 240, 160);
                elementaryCurvePlot.setTypeOfPlot("elementaryCurve");
                setUpVertexes(elementaryCurvePlot, true);
                firstDrawingPanel.add(elementaryCurvePlot);
                firstMainPanel.add(firstDrawingPanel, BorderLayout.WEST);
                firstMainPanel.add(firstInputPanel, BorderLayout.EAST);

                JPanel secondMainPanel = new JPanel();
                secondInputPanel = new JPanel();
                secondDrawingPanel = new JPanel();
                setJPanels(secondInputPanel, secondDrawingPanel);
                sharkPlot = new Plot();
                ArrayList<Point> sharksVertexes = sharkPlot.buildSharksPlot();
                ComplexPoint firstPoint = new ComplexPoint(sharksVertexes.get(0), new Point(0, 0));
                sharkPlot.createComplexVertexes(firstPoint, sharksVertexes);
                sharkPlot.setTypeOfPlot("elementaryCurve");
                setUpVertexes(sharkPlot, true);
                secondDrawingPanel.add(sharkPlot);
                secondMainPanel.add(secondDrawingPanel, BorderLayout.WEST);
                secondMainPanel.add(secondInputPanel, BorderLayout.EAST);

                JPanel thirdMainPanel = new JPanel();
                thirdInputPanel = new JPanel();
                thirdDrawingPanel = new JPanel();
                setJPanels(thirdInputPanel, thirdDrawingPanel);
                gridPlot = new Plot();
                setUpInitPoints(gridPlot, 10, 230, 25, 40, 50, 150, 200, 170, 240, 160);
                gridPlot.setTypeOfPlot("grid");
                setUpVertexes(gridPlot, true);
                thirdDrawingPanel.add(gridPlot);
                thirdMainPanel.add(thirdDrawingPanel, BorderLayout.WEST);
                thirdMainPanel.add(thirdInputPanel, BorderLayout.EAST);

                JPanel fourthMainPanel = new JPanel();
                fourthInputPanel = new JPanel();
                fourthDrawingPanel = new JPanel();
                setJPanels(fourthInputPanel, fourthDrawingPanel);
                surfacePlot = new Plot();
                setUpComplexVertexes(surfacePlot, 150, 120, 50, 40, 40, 22, 50, 30);
                surfacePlot.setTypeOfPlot("surface");
                setUpVertexes(surfacePlot, true);
                fourthDrawingPanel.add(surfacePlot);
                fourthMainPanel.add(fourthDrawingPanel, BorderLayout.WEST);
                fourthMainPanel.add(fourthInputPanel, BorderLayout.EAST);

                tabbedPane.addTab(firstTabName, firstMainPanel);
                tabbedPane.addTab(secondTabName, secondMainPanel);
                tabbedPane.addTab(thirdTabName, thirdMainPanel);
                tabbedPane.addTab(fourthTabName, fourthMainPanel);
                content.add(tabbedPane, BorderLayout.CENTER);
                frame.add(content);
                inputDataForThePlot();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setJPanels(JPanel inputPanel, JPanel drawingPanel) {
        inputPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        inputPanel.setFont(font);
        inputPanel.setPreferredSize(new Dimension(frame.getWidth() / 5, frame.getHeight()));
        inputPanel.setVisible(true);
        inputPanel.setLayout(null);
        drawingPanel.setVisible(true);
    }

    private JTextField createTextField(int bounds[], String value, JPanel panel) {
        JTextField textField = new JTextField(5);
        textField.setFont(font);
        textField.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        if (!Objects.equals(value, "")) textField.setText(value);
        panel.add(textField);
        return textField;
    }

    private JButton createButton(int bounds[], String iconName, JPanel panel, ActionListener handler) {
        JButton button = new JButton();
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        try {
            Image img = ImageIO.read(getClass().getResource(iconName));
            button.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        button.setBackground(Color.WHITE);
        button.addActionListener(handler);
        panel.add(button);
        return button;
    }

    private void createLabel(String title, int bounds[], JPanel panel) {
        JLabel label = new JLabel(title);
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        panel.add(label);
    }

    private void createInterfaceOfFirstInputPanel(JCheckBox checkBox){
        JRadioButton btnOfRealPartDisplay = new JRadioButton("Re");
        JRadioButton btnOfImaginaryPartDisplay = new JRadioButton("Im");
        initRadioButton(firstInputPanel, elementaryCurvePlot, btnOfRealPartDisplay, btnOfImaginaryPartDisplay);

        createLabel("x0 (Re) : ", new int[]{20, 120, 50, 20}, firstInputPanel);
        JTextField x0_ReTF = createTextField(new int[]{70, 120, 40, 20}, "10", firstInputPanel);

        createLabel("y0 (Re) : ", new int[]{20, 150, 50, 20}, firstInputPanel);
        JTextField y0_ReTF = createTextField(new int[]{70, 150, 40, 20}, "230", firstInputPanel);

        createLabel("x0 (Im) : ", new int[]{130, 120, 50, 20}, firstInputPanel);
        JTextField x0_ImTF = createTextField(new int[]{180, 120, 40, 20}, "25", firstInputPanel);

        createLabel("y0 (Im) : ", new int[]{130, 150, 50, 20}, firstInputPanel);
        JTextField y0_ImTF = createTextField(new int[]{180, 150, 40, 20}, "40", firstInputPanel);

        createLabel("x1 (Re) : ", new int[]{20, 180, 50, 20}, firstInputPanel);
        JTextField x1_ReTF = createTextField(new int[]{70, 180, 40, 20}, "50", firstInputPanel);

        createLabel("y1 (Re) : ", new int[]{20, 210, 50, 20}, firstInputPanel);
        JTextField y1_ReTF = createTextField(new int[]{70, 210, 40, 20}, "150", firstInputPanel);

        createLabel("x2 (Re) : ", new int[]{130, 180, 50, 20}, firstInputPanel);
        JTextField x2_ReTF = createTextField(new int[]{180, 180, 40, 20}, "200", firstInputPanel);

        createLabel("y2 (Re) : ", new int[]{130, 210, 50, 20}, firstInputPanel);
        JTextField y2_ReTF = createTextField(new int[]{180, 210, 40, 20}, "170", firstInputPanel);

        createLabel("x3 (Re) : ", new int[]{20, 240, 50, 20}, firstInputPanel);
        JTextField x3_ReTF = createTextField(new int[]{70, 240, 40, 20}, "240", firstInputPanel);

        createLabel("y3 (Re) : ", new int[]{20, 270, 50, 20}, firstInputPanel);
        JTextField y3_ReTF = createTextField(new int[]{70, 270, 40, 20}, "160", firstInputPanel);
        ArrayList<JTextField> txtFields = new ArrayList<>();
        txtFields.add(x0_ReTF);
        txtFields.add(y0_ReTF);
        txtFields.add(x0_ImTF);
        txtFields.add(y0_ImTF);
        txtFields.add(x1_ReTF);
        txtFields.add(y1_ReTF);
        txtFields.add(x2_ReTF);
        txtFields.add(y2_ReTF);
        txtFields.add(x3_ReTF);
        txtFields.add(y3_ReTF);

        JButton btn = createButton(new int[]{160, 245, 40, 40}, "Images/executing.png", firstInputPanel, (ActionEvent event) -> {
            checkBox.setSelected(false);
            drawNewPlot(firstDrawingPanel, elementaryCurvePlot, btnOfRealPartDisplay.isSelected(), txtFields);
        });

        firstDrawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                showCoordinates(e, elementaryCurvePlot);
            }
        });
    }

    private void createInterfaceOfThirdInputPanel(JCheckBox checkBox){
        JRadioButton btnOfRealPartDisplay = new JRadioButton("Re");
        JRadioButton btnOfImaginaryPartDisplay = new JRadioButton("Im");
        initRadioButton(thirdInputPanel, gridPlot, btnOfRealPartDisplay, btnOfImaginaryPartDisplay);

        createLabel("x0 (Re) : ", new int[]{20, 120, 50, 20}, thirdInputPanel);
        JTextField x0_ReTF = createTextField(new int[]{70, 120, 40, 20}, "10", thirdInputPanel);

        createLabel("y0 (Re) : ", new int[]{20, 150, 50, 20}, thirdInputPanel);
        JTextField y0_ReTF = createTextField(new int[]{70, 150, 40, 20}, "230", thirdInputPanel);

        createLabel("x0 (Im) : ", new int[]{130, 120, 50, 20}, thirdInputPanel);
        JTextField x0_ImTF = createTextField(new int[]{180, 120, 40, 20}, "25", thirdInputPanel);

        createLabel("y0 (Im) : ", new int[]{130, 150, 50, 20}, thirdInputPanel);
        JTextField y0_ImTF = createTextField(new int[]{180, 150, 40, 20}, "40", thirdInputPanel);

        createLabel("x1 (Re) : ", new int[]{20, 180, 50, 20}, thirdInputPanel);
        JTextField x1_ReTF = createTextField(new int[]{70, 180, 40, 20}, "50", thirdInputPanel);

        createLabel("y1 (Re) : ", new int[]{20, 210, 50, 20}, thirdInputPanel);
        JTextField y1_ReTF = createTextField(new int[]{70, 210, 40, 20}, "150", thirdInputPanel);

        createLabel("x2 (Re) : ", new int[]{130, 180, 50, 20}, thirdInputPanel);
        JTextField x2_ReTF = createTextField(new int[]{180, 180, 40, 20}, "200", thirdInputPanel);

        createLabel("y2 (Re) : ", new int[]{130, 210, 50, 20}, thirdInputPanel);
        JTextField y2_ReTF = createTextField(new int[]{180, 210, 40, 20}, "170", thirdInputPanel);

        createLabel("x3 (Re) : ", new int[]{20, 240, 50, 20}, thirdInputPanel);
        JTextField x3_ReTF = createTextField(new int[]{70, 240, 40, 20}, "240", thirdInputPanel);

        createLabel("y3 (Re) : ", new int[]{20, 270, 50, 20}, thirdInputPanel);
        JTextField y3_ReTF = createTextField(new int[]{70, 270, 40, 20}, "160", thirdInputPanel);
        ArrayList<JTextField> txtFields = new ArrayList<>();
        txtFields.add(x0_ReTF);
        txtFields.add(y0_ReTF);
        txtFields.add(x0_ImTF);
        txtFields.add(y0_ImTF);
        txtFields.add(x1_ReTF);
        txtFields.add(y1_ReTF);
        txtFields.add(x2_ReTF);
        txtFields.add(y2_ReTF);
        txtFields.add(x3_ReTF);
        txtFields.add(y3_ReTF);

        JButton btn = createButton(new int[]{160, 245, 40, 40}, "Images/executing.png", thirdInputPanel, (ActionEvent event) -> {
            checkBox.setSelected(false);
            drawNewPlot(thirdDrawingPanel, gridPlot, btnOfRealPartDisplay.isSelected(), txtFields);
        });
    }

    private void createInterfaceOfFourthInputPanel(JCheckBox checkBox){
        JRadioButton btnOfRealPartDisplay = new JRadioButton("Re");
        JRadioButton btnOfImaginaryPartDisplay = new JRadioButton("Im");
        initRadioButton(fourthInputPanel, surfacePlot, btnOfRealPartDisplay, btnOfImaginaryPartDisplay);

        createLabel("a0 = ", new int[]{20, 90, 30, 20}, fourthInputPanel);
        JTextField a0_ReTF = createTextField(new int[]{50, 90, 40, 20}, "150", fourthInputPanel);
        createLabel("+", new int[]{95, 90, 10, 20}, fourthInputPanel);
        JTextField a0_ImTF = createTextField(new int[]{110, 90, 40, 20}, "120", fourthInputPanel);
        createLabel("i", new int[]{153, 90, 2, 20}, fourthInputPanel);

        createLabel("a1 = ", new int[]{20, 120, 30, 20}, fourthInputPanel);
        JTextField a1_ReTF = createTextField(new int[]{50, 120, 40, 20}, "50", fourthInputPanel);
        createLabel("+", new int[]{95, 120, 10, 20}, fourthInputPanel);
        JTextField a1_ImTF = createTextField(new int[]{110, 120, 40, 20}, "40", fourthInputPanel);
        createLabel("i", new int[]{153, 120, 2, 20}, fourthInputPanel);

        createLabel("a2 = ", new int[]{20, 150, 30, 20}, fourthInputPanel);
        JTextField a2_ReTF = createTextField(new int[]{50, 150, 40, 20}, "40", fourthInputPanel);
        createLabel("+", new int[]{95, 150, 10, 20}, fourthInputPanel);
        JTextField a2_ImTF = createTextField(new int[]{110, 150, 40, 20}, "22", fourthInputPanel);
        createLabel("i", new int[]{153, 150, 2, 20}, fourthInputPanel);

        createLabel("a3 = ", new int[]{20, 180, 30, 20}, fourthInputPanel);
        JTextField a3_ReTF = createTextField(new int[]{50, 180, 40, 20}, "50", fourthInputPanel);
        createLabel("+", new int[]{95, 180, 10, 20}, fourthInputPanel);
        JTextField a3_ImTF = createTextField(new int[]{110, 180, 40, 20}, "30", fourthInputPanel);
        createLabel("i", new int[]{153, 180, 2, 20}, fourthInputPanel);

        ArrayList<JTextField> txtFields = new ArrayList<>();
        txtFields.add(a0_ReTF);
        txtFields.add(a0_ImTF);
        txtFields.add(a1_ReTF);
        txtFields.add(a1_ImTF);
        txtFields.add(a2_ReTF);
        txtFields.add(a2_ImTF);
        txtFields.add(a3_ReTF);
        txtFields.add(a3_ImTF);

        JButton btn = createButton(new int[]{170, 125, 40, 40}, "Images/executing.png", fourthInputPanel, (ActionEvent event) -> {
            checkBox.setSelected(false);
            drawNewSurface(fourthDrawingPanel, surfacePlot, btnOfRealPartDisplay.isSelected(), txtFields);
        });

        createLabel("<html>Перемещение</html>", new int[] {70, 210, 250, 20}, fourthInputPanel);
        createLabel("∆x: ", new int[] {40, 240, 30, 20}, fourthInputPanel);
        JTextField deltaX = createTextField(new int[] {60, 240, 60, 20}, "", fourthInputPanel);

        createLabel("∆y: ", new int[] {40, 270, 30, 20}, fourthInputPanel);
        JTextField deltaY = createTextField(new int[] {60, 270, 60, 20}, "", fourthInputPanel);

        createLabel("∆z: ", new int[] {40, 300, 30, 20}, fourthInputPanel);
        JTextField deltaZ = createTextField(new int[] {60, 300, 60, 20}, "", fourthInputPanel);

        JButton btnOfMoving = createButton(new int[] {170, 260, 40, 40}, "Images/moving.png", fourthInputPanel, event -> {
            float delta_x = Float.parseFloat(deltaX.getText());
            float delta_y = Float.parseFloat(deltaY.getText());
            float delta_z = Float.parseFloat(deltaZ.getText());

            fourthDrawingPanel.remove(surfacePlot);
            surfacePlot.move(delta_x, delta_y, delta_z);
            fourthDrawingPanel.add(surfacePlot);
            fourthDrawingPanel.repaint();
            fourthDrawingPanel.revalidate();
        });

        createLabel("<html>Вращение</html>", new int[] {100, 330, 250, 20}, fourthInputPanel);
        JRadioButton btnOfRotatingByX = new JRadioButton("X");
        btnOfRotatingByX.setMnemonic(KeyEvent.VK_X);
        btnOfRotatingByX.setActionCommand("X");
        btnOfRotatingByX.setSelected(false);

        JRadioButton btnOfRotatingByY = new JRadioButton("Y");
        btnOfRotatingByY.setMnemonic(KeyEvent.VK_Y);
        btnOfRotatingByY.setActionCommand("Y");
        btnOfRotatingByY.setSelected(false);

        JRadioButton btnOfRotatingByZ = new JRadioButton("Z");
        btnOfRotatingByZ.setMnemonic(KeyEvent.VK_Z);
        btnOfRotatingByZ.setActionCommand("Z");
        btnOfRotatingByZ.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(btnOfRotatingByX);
        group.add(btnOfRotatingByY);
        group.add(btnOfRotatingByZ);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(btnOfRotatingByX);
        verticalBox.add(btnOfRotatingByY);
        verticalBox.add(btnOfRotatingByZ);
        btnOfRotatingByX.setVisible(true);
        btnOfRotatingByY.setVisible(true);
        btnOfRotatingByZ.setVisible(true);

        createLabel("x : ", new int[] {40, 360, 30, 20}, fourthInputPanel);
        JTextField x = createTextField(new int[] {60, 360, 60, 20}, "", fourthInputPanel);

        createLabel("y : ", new int[] {40, 390, 30, 20}, fourthInputPanel);
        JTextField y = createTextField(new int[] {60, 390, 60, 20}, "", fourthInputPanel);

        createLabel("z : ", new int[] {40, 420, 30, 20}, fourthInputPanel);
        JTextField z = createTextField(new int[] {60, 420, 60, 20}, "", fourthInputPanel);

        createLabel("∠ : ", new int[] {40, 450, 20, 20}, fourthInputPanel);
        JTextField angleForRotation = createTextField(new int[] {60, 450, 60, 20}, "", fourthInputPanel);

        JButton btnOfRotation = createButton(new int[]{170, 430, 40, 40}, "Images/rotating.png", fourthInputPanel, event -> {
            float x0 = Float.parseFloat(x.getText());
            float y0 = Float.parseFloat(y.getText());
            float z0 = Float.parseFloat(z.getText());
            double angle = Double.parseDouble(angleForRotation.getText());

            String axis = "";
            if (btnOfRotatingByX.isSelected()){
                axis = "X";
            } else if (btnOfRotatingByY.isSelected()){
                axis = "Y";
            } else if (btnOfRotatingByZ.isSelected()){
                axis = "Z";
            }

            surfacePlot.setRotationAngle(surfacePlot.getRotationAngle() + angle);
            fourthDrawingPanel.remove(surfacePlot);
            surfacePlot.rotate(new Point(x0, y0, z0), angle, axis);
            fourthDrawingPanel.add(surfacePlot);
            fourthDrawingPanel.validate();
            fourthDrawingPanel.repaint();
        });

        fourthInputPanel.add(verticalBox);
        verticalBox.setBounds(180, 360, 40, 65);
        verticalBox.setVisible(true);
    }

    private void drawNewPlot(JPanel panel, Plot plot, boolean choice, ArrayList<JTextField> txtFields){
        float x0_Re = parseFloat(txtFields.get(0).getText());
        float y0_Re = parseFloat(txtFields.get(1).getText());
        float x0_Im = parseFloat(txtFields.get(2).getText());
        float y0_Im = parseFloat(txtFields.get(3).getText());
        float x1_Re = parseFloat(txtFields.get(4).getText());
        float y1_Re = parseFloat(txtFields.get(5).getText());
        float x2_Re = parseFloat(txtFields.get(6).getText());
        float y2_Re = parseFloat(txtFields.get(7).getText());
        float x3_Re = parseFloat(txtFields.get(8).getText());
        float y3_Re = parseFloat(txtFields.get(9).getText());

        setUpInitPoints(plot, x0_Re, y0_Re, x0_Im, y0_Im, x1_Re, y1_Re, x2_Re, y2_Re, x3_Re, y3_Re);
        setUpVertexes(plot, choice);

        panel.add(plot);
        panel.validate();
        panel.repaint();
    }

    private void drawNewSurface(JPanel panel, Plot plot, boolean choice, ArrayList<JTextField> txtFields){
        float a0_Re = parseFloat(txtFields.get(0).getText());
        float a0_Im = parseFloat(txtFields.get(1).getText());
        float a1_Re = parseFloat(txtFields.get(2).getText());
        float a1_Im = parseFloat(txtFields.get(3).getText());
        float a2_Re = parseFloat(txtFields.get(4).getText());
        float a2_Im = parseFloat(txtFields.get(5).getText());
        float a3_Re = parseFloat(txtFields.get(6).getText());
        float a3_Im = parseFloat(txtFields.get(7).getText());

        setUpComplexVertexes(surfacePlot, a0_Re, a0_Im, a1_Re, a1_Im, a2_Re, a2_Im, a3_Re, a3_Im);
        setUpVertexes(surfacePlot, choice);

        panel.add(plot);
        panel.validate();
        panel.repaint();
    }

    private void initRadioButton(JPanel panel, Plot plot, JRadioButton btnOfRealPartDisplay, JRadioButton btnOfImaginaryPartDisplay){
        createLabel("<html>Отобразить:</html>", new int[]{90, 20, 65, 20}, panel);

        btnOfRealPartDisplay.setMnemonic(KeyEvent.VK_R);
        btnOfRealPartDisplay.setActionCommand("Re");
        btnOfRealPartDisplay.setSelected(true);
        btnOfRealPartDisplay.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                plot.setDisplay("Re");
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(firstTabName)) {
                    plot.setVertexes(plot.getVertexesRe());
                }
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                plot.setDisplay("Im");
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(firstTabName)) {
                    plot.setVertexes(plot.getVertexesIm());
                }
            }
        });

        btnOfImaginaryPartDisplay.setMnemonic(KeyEvent.VK_I);
        btnOfImaginaryPartDisplay.setActionCommand("Im");
        btnOfImaginaryPartDisplay.setSelected(false);
        btnOfImaginaryPartDisplay.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                btnOfRealPartDisplay.setSelected(false);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                btnOfRealPartDisplay.setSelected(true);
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(btnOfRealPartDisplay);
        group.add(btnOfImaginaryPartDisplay);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(btnOfRealPartDisplay);
        verticalBox.add(btnOfImaginaryPartDisplay);
        btnOfRealPartDisplay.setVisible(true);
        btnOfImaginaryPartDisplay.setVisible(true);

        panel.add(verticalBox);
        verticalBox.setBounds(103, 40,40,43);
        verticalBox.setVisible(true);
    }

    private void inputDataForThePlot() {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText("Show quadrangles");
        checkBox.addActionListener(e -> {
            JCheckBox choice = (JCheckBox) e.getSource();
            if (choice.isSelected()) {
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(firstTabName)) {
                    elementaryCurvePlot.setVisibilityOfQuadrangles(true);
                    firstDrawingPanel.validate();
                    firstDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(secondTabName)) {
                    sharkPlot.setVisibilityOfQuadrangles(true);
                    secondDrawingPanel.validate();
                    secondDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(thirdTabName)) {
                    gridPlot.setVisibilityOfQuadrangles(true);
                    thirdDrawingPanel.validate();
                    thirdDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(fourthTabName)) {
                    surfacePlot.setVisibilityOfQuadrangles(true);
                    fourthDrawingPanel.validate();
                    fourthDrawingPanel.repaint();
                }
            } else {
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(firstTabName)) {
                    elementaryCurvePlot.setVisibilityOfQuadrangles(false);
                    firstDrawingPanel.validate();
                    firstDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(secondTabName)) {
                    sharkPlot.setVisibilityOfQuadrangles(false);
                    secondDrawingPanel.validate();
                    secondDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(thirdTabName)) {
                    gridPlot.setVisibilityOfQuadrangles(false);
                    thirdDrawingPanel.validate();
                    thirdDrawingPanel.repaint();
                }
                if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals(fourthTabName)) {
                    surfacePlot.setVisibilityOfQuadrangles(false);
                    fourthDrawingPanel.validate();
                    fourthDrawingPanel.repaint();
                }
            }
        });

        tabbedPane.addChangeListener(e -> checkBox.setSelected(false));
        checkBox.setBounds(1080, 10, 120, 20);
        buttonsPanel.add(checkBox);
        createInterfaceOfFirstInputPanel(checkBox);
        createInterfaceOfSecondInputPanel(checkBox);
        createInterfaceOfThirdInputPanel(checkBox);
        createInterfaceOfFourthInputPanel(checkBox);
    }

    private void createInterfaceOfSecondInputPanel(JCheckBox checkBox){
        createLabel("<html>Анимировать переход к другой системе координат:</html>", new int[]{20, 20, 250, 30}, secondInputPanel);
        JButton btnExecuting = createButton(new int[]{140, 40, 40, 40}, "Images/executing.png", secondInputPanel, event -> {
            try {
                AnimationTask animationTask = new AnimationTask(sharkPlot);
                checkBox.setSelected(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void setUpInitPoints(Plot plot, float x0_Re, float y0_Re, float x0_Im, float y0_Im, float x1_Re, float y1_Re, float x2_Re, float y2_Re, float x3_Re, float y3_Re) {
        Point startPointRe = new Point(x0_Re, y0_Re);
        ComplexPoint startPoint = new ComplexPoint(startPointRe, new Point(x0_Im, y0_Im));
        Point secondPointRe = new Point(x1_Re, y1_Re, TypeOfPoint.Re);
        Point thirdPointRe = new Point(x2_Re, y2_Re, TypeOfPoint.Re);
        Point endPointRe = new Point(x3_Re, y3_Re, TypeOfPoint.Re);
        ArrayList<Point> points = new ArrayList<>();
        points.add(startPointRe);
        points.add(secondPointRe);
        points.add(thirdPointRe);
        points.add(endPointRe);
        plot.createComplexVertexes(startPoint, points);
    }

    private void setUpComplexVertexes(Plot plot, float a0_Re, float a0_Im, float a1_Re, float a1_Im, float a2_Re, float a2_Im, float a3_Re, float a3_Im){
        ArrayList<Complex> coefficients = new ArrayList<>();
        coefficients.add(new Complex(a0_Re, a0_Im));
        coefficients.add(new Complex(a1_Re, a1_Im));
        coefficients.add(new Complex(a2_Re, a2_Im));
        coefficients.add(new Complex(a3_Re, a3_Im));
        plot.createComplexVertexes(coefficients);
    }

    private void setUpVertexes(Plot plot, boolean choice){
        plot.initReImVertexes();
        if (choice) {
            plot.setDisplay("Re");
            plot.setVertexes(plot.getVertexesRe());
        }
        else {
            plot.setDisplay("Im");
            plot.setVertexes(plot.getVertexesIm());
        }
    }

    private void showCoordinates(MouseEvent e, Plot plot) {
        JTextField coordinates = createTextField(new int[]{85, 400, 75, 25}, "coordinates", firstInputPanel);
        double distance = Double.MAX_VALUE;
        Point nearestPoint = null;
        for (Point point : plot.getPoints()) {
            Point position = new Point(e.getX() - firstDrawingPanel.getWidth() / 2, -(e.getY() - firstDrawingPanel.getHeight() / 2));
            double newDistance = plot.getDistance(point, position);
            if (newDistance < 5 && newDistance < distance) {
                nearestPoint = point;
                distance = newDistance;
            }
        }
        if (distance != Double.MAX_VALUE) {
            coordinates.setVisible(true);
            coordinates.setText("x: " + Math.round(nearestPoint.getX()) + "; y: " + Math.round(nearestPoint.getY()));
        }
    }
}

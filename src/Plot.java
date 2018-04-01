import org.apache.commons.math3.complex.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Plot extends JPanel {
    private double unitVectorSize;
    private Graphics2D graphics;
    private double x0;
    private double y0;
    private boolean visibilityOfQuadrangles;
    private ArrayList<ComplexPoint> complexVertexes;
    private ArrayList<Point> vertexesRe, vertexesIm;
    private ArrayList<Point> points, vertexes;
    private String display;
    private String typeOfPlot;

    public boolean isVisibilityOfQuadrangles() {
        return visibilityOfQuadrangles;
    }

    public void setVisibilityOfQuadrangles(boolean visibilityOfQuadrangles) {
        this.visibilityOfQuadrangles = visibilityOfQuadrangles;
    }

    public ArrayList<ComplexPoint> getComplexVertexes() {
        return complexVertexes;
    }

    public void setComplexVertexes(ArrayList<ComplexPoint> complexVertexes) {
        this.complexVertexes = complexVertexes;
    }

    public ArrayList<Point> getVertexesRe() {
        return vertexesRe;
    }

    public void setVertexesRe(ArrayList<Point> vertexesRe) {
        this.vertexesRe = vertexesRe;
    }

    public ArrayList<Point> getVertexesIm() {
        return vertexesIm;
    }

    public void setVertexesIm(ArrayList<Point> vertexesIm) {
        this.vertexesIm = vertexesIm;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ArrayList<Point> getVertexes() {
        return vertexes;
    }

    public void setVertexes(ArrayList<Point> vertexes) {
        this.vertexes = vertexes;
    }

    public String getTypeOfPlot() {
        return typeOfPlot;
    }

    public void setTypeOfPlot(String typeOfPlot) {
        this.typeOfPlot = typeOfPlot;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 720);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // set the canvas origin (0,0) to center canvas
        // All coordinates to the left of center canvas are negative
        // All coordinates below center canvas are negative
        this.graphics = (Graphics2D) graphics;
        this.graphics.translate(getWidth() / 2, getHeight() / 2);
        drawLinesForCoordinateGrid();
        drawAxis();
        calculateCurveLength();
        createPlot(typeOfPlot);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 13));
        switch (display) {
            case "Re":
                graphics.drawString("yRe", (int) unitVectorSize, -getHeight() / 2 + 15);
                graphics.drawString("xRe", (int) (getWidth() / 2 - unitVectorSize * 1.5), -15);
                break;
            case "Im":
                graphics.drawString("yIm", (int) unitVectorSize, -getHeight() / 2 + 15);
                graphics.drawString("xIm", (int) (getWidth() / 2 - unitVectorSize * 1.5), -15);
                break;
        }
    }

    public Plot() {
        unitVectorSize = 20;
        x0 = 0;
        y0 = 0;
        points = new ArrayList<>();
        vertexes = new ArrayList<>();
    }

    private void drawLinesForCoordinateGrid() {
        for (int i = 0; i < getWidth(); i += unitVectorSize) {
            //vertical lines
            drawLine(new Point(x0 - getWidth() / 2 + i, y0 + getHeight()), new Point(x0 - getWidth() / 2 + i, y0 - getHeight()), TypeOfLine.COORDINATEGridLine);
        }
        for (int i = 0; i < getHeight(); i += unitVectorSize) {
            //horizontal lines
            drawLine(new Point(x0 - getWidth(), y0 - getHeight() / 2 + i), new Point(x0 + getWidth(), y0 - getHeight() / 2 + i), TypeOfLine.COORDINATEGridLine);
        }
        drawLine(new Point(unitVectorSize, -2), new Point(unitVectorSize, 2), TypeOfLine.COORDINATEGridLine);
        drawLine(new Point(-2, unitVectorSize), new Point(2, unitVectorSize), TypeOfLine.COORDINATEGridLine);
    }

    private void drawAxis() {
        //x and y lines
        drawLine(new Point(0, -getHeight() / 2), new Point(0, getHeight() / 2), TypeOfLine.AXISLine);
        drawLine(new Point(-10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine);
        drawLine(new Point(10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine);

        drawLine(new Point(-getWidth() / 2, 0), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine);
        drawLine(new Point(getWidth() / 2 - 10, -10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine);
        drawLine(new Point(getWidth() / 2 - 10, 10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 11));
        graphics.drawString("20", (int) unitVectorSize - 12, 10);
        graphics.drawString("20", 4, (int) -unitVectorSize);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
    }

    public void drawLine(Point point1, Point point2, TypeOfLine typeOfLine) {
        switch (typeOfLine) {
            case COORDINATEGridLine:
                graphics.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                graphics.setColor(new Color(0f, 0f, 0f, .4f));
                break;
            case AXISLine:
                graphics.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                graphics.setColor(new Color(0f, 0f, 0f, .6f));
                break;
            case CURVELine:
                graphics.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                graphics.setColor(new Color(0f, 0f, 0f, 1f));
                points.add(point1);
                points.add(point2);
                break;
            case COMPLEXCurveLine:
                graphics.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                graphics.setColor(new Color(0f, 0f, 0f, 1f));
                points.add(point1);
                points.add(point2);
                break;
            case DIMENSIONLine:
                graphics.setColor(new Color(0f, 0f, 1f, 1f));
                graphics.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                points.add(point1);
                points.add(point2);
                break;
        }
        graphics.draw(new Line2D.Double(point1.getX(), -point1.getY(), point2.getX(), -point2.getY()));
    }

    private void drawCharacterQuadrangles(ArrayList<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            drawLine(points.get(i), points.get(i + 1), TypeOfLine.DIMENSIONLine);
        }
    }

    private void drawVertexes(ArrayList<Point> points) {
        int size = 6;
        for (Point point : points) {
            graphics.fillOval((int) point.getX() - size / 2, -(int) point.getY() - size / 2, size, size);
            graphics.drawString(Integer.toString(points.indexOf(point)), (int) point.getX() + size, -(int) point.getY());
        }
    }

    private ComplexPoint createComplexPoint(ComplexPoint previousComplexPoint, Point currentPointRe) {
        double currentXIm = -currentPointRe.getY() + previousComplexPoint.getPointIm().getX() + previousComplexPoint.getPointRe().getY();
        double currentYIm = currentPointRe.getX() - previousComplexPoint.getPointRe().getX() + previousComplexPoint.getPointIm().getY();
        return new ComplexPoint(currentPointRe, new Point(currentXIm, currentYIm));
    }

    public void createComplexVertexes(ComplexPoint startPoint, ArrayList<Point> pointsRe) {
        complexVertexes = new ArrayList<>();
        vertexesRe = new ArrayList<>();
        vertexesIm = new ArrayList<>();
        complexVertexes.add(startPoint);

        for (int i = 1; i < pointsRe.size(); i++) {
            ComplexPoint point = createComplexPoint(startPoint, pointsRe.get(i));
            complexVertexes.add(point);
            startPoint = point;
        }
    }

    public void initReImVertexes(){
        for (ComplexPoint complexPoint : complexVertexes) {
            vertexesRe.add(complexPoint.getPointRe());
            vertexesIm.add(complexPoint.getPointIm());
        }
    }

    private void createPlot(String typeOfPlot) {
        if (visibilityOfQuadrangles) {
            drawCharacterQuadrangles(vertexes);
            drawVertexes(vertexes);
            visibilityOfQuadrangles = false;
        }
        switch (typeOfPlot){
            case "elementaryCurve":
                PlotOfElementaryCurve plotOfElementaryCurve = new PlotOfElementaryCurve(this);
                plotOfElementaryCurve.draw(vertexes);
                break;
            case "grid":
                Grid grid = new Grid(this);
                grid.draw(complexVertexes);
                break;
            case "surface":
                Surface surface = new Surface(this);
                surface.draw(complexVertexes);
                break;
        }
    }

    public void createComplexVertexes(ArrayList<Complex> coefficients){
        ArrayList<ComplexPoint> complexVertexes = new ArrayList<>();
        Complex r0_x = (coefficients.get(0).subtract(coefficients.get(2))).multiply(Complex.I);
        Complex r0_y = coefficients.get(0).add(coefficients.get(2));
        Complex r0_z = coefficients.get(1).multiply(-1).multiply(Complex.I);

        Complex r1_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3))).multiply(Complex.I);
        Complex r1_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3));
        Complex r1_z = coefficients.get(1).multiply(-1).multiply(Complex.I);

        Complex r2_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3).multiply(2))).multiply(Complex.I);
        Complex r2_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3).multiply(2));
        Complex r2_z = (coefficients.get(3).subtract(coefficients.get(1))).multiply(Complex.I);

        Complex r3_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3).multiply(2))).multiply(Complex.I);
        Complex r3_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3).multiply(4));
        Complex r3_z = (coefficients.get(3).multiply(3).subtract(coefficients.get(1))).multiply(Complex.I);

        complexVertexes.add(new ComplexPoint(new Point(r0_x.getReal(), r0_y.getReal(), r0_z.getReal()), new Point(r0_x.getImaginary(), r0_y.getImaginary(), r0_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r1_x.getReal(), r1_y.getReal(), r1_z.getReal()), new Point(r1_x.getImaginary(), r1_y.getImaginary(), r1_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r2_x.getReal(), r0_y.getReal(), r2_z.getReal()), new Point(r2_x.getImaginary(), r2_y.getImaginary(), r2_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r3_x.getReal(), r3_y.getReal(), r3_z.getReal()), new Point(r3_x.getImaginary(), r3_y.getImaginary(), r3_z.getImaginary())));
        vertexesRe = new ArrayList<>();
        vertexesIm = new ArrayList<>();
        setComplexVertexes(complexVertexes);
    }

    private void calculateCurveLength() {
        double u = 0.1;
        int counter = 0;
        for (int i = 0; i < complexVertexes.size() - 3; i += 3){
            counter++;
            System.out.println("curve = " + counter);
            for (double t = 0; t < 1 + u; t += u) {
                Complex x_1 = complexVertexes.get(i + 1).getComplexX().subtract(complexVertexes.get(i).getComplexX()).multiply(3).multiply(Math.pow(1 - t, 2));
                Complex x_2 = complexVertexes.get(i + 2).getComplexX().subtract(complexVertexes.get(i + 1).getComplexX()).multiply(6).multiply((1 - t) * t);
                Complex x_3 = complexVertexes.get(i + 3).getComplexX().subtract(complexVertexes.get(i + 2).getComplexX()).multiply(3).multiply(Math.pow(t, 2));
                Complex x_ = x_1.add(x_2).add(x_3);
                Complex y_1 = complexVertexes.get(i + 1).getComplexY().subtract(complexVertexes.get(i).getComplexY()).multiply(3).multiply(Math.pow(1 - t, 2));
                Complex y_2 = complexVertexes.get(i + 2).getComplexY().subtract(complexVertexes.get(i + 1).getComplexY()).multiply(6).multiply((1 - t) * t);
                Complex y_3 = complexVertexes.get(i + 3).getComplexY().subtract(complexVertexes.get(i + 2).getComplexY()).multiply(3).multiply(Math.pow(t, 2));
                Complex y_ = y_1.add(y_2).add(y_3);
                Complex result = x_.pow(2).add(y_.pow(2));
                System.out.println("u = " + t + ": length = " + result.abs());
            }
        }
    }

    public double getDistance(Point p1, Point p2) {
        double a = p1.getX() - p2.getX();
        double b = p1.getY() - p2.getY();
        return Math.sqrt(a * a + b * b);
    }

    public ArrayList<Point> buildSharksPlot() {
        ArrayList<Point> vertexesOfShark = new ArrayList<>();
        //1
        vertexesOfShark.add(new Point(-140, -100, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-300, -90, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-350, -40, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-400, 0, TypeOfPoint.Re));
        //2
        vertexesOfShark.add(new Point(-425, 20, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-418, 43, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-410, 70, TypeOfPoint.Re));
        //3
        vertexesOfShark.add(new Point(-350, 150, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-300, 145, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-250, 140, TypeOfPoint.Re));
        //4
        vertexesOfShark.add(new Point(-260, 170, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-265, 215, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-270, 260, TypeOfPoint.Re));
        //5
        vertexesOfShark.add(new Point(-265, 340, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-243, 300, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-220, 260, TypeOfPoint.Re));
        //6
        vertexesOfShark.add(new Point(-176, 180, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-173, 170, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-170, 160, TypeOfPoint.Re));
        //7
        vertexesOfShark.add(new Point(-110, 235, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-60, 257, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-10, 280, TypeOfPoint.Re));
        //8
        vertexesOfShark.add(new Point(30, 300, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(35, 287, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(40, 275, TypeOfPoint.Re));
        //9
        vertexesOfShark.add(new Point(5, 200, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(2, 145, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(0, 100, TypeOfPoint.Re));
        //10
        vertexesOfShark.add(new Point(20, 98, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(30, 95, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(40, 93, TypeOfPoint.Re));
        //11
        vertexesOfShark.add(new Point(52, 118, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(65, 134, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(78, 150, TypeOfPoint.Re));
        //12
        vertexesOfShark.add(new Point(107, 162, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(108.5, 152, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(110, 142, TypeOfPoint.Re));
        //13
        vertexesOfShark.add(new Point(105, 121, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(105, 99, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(105, 77, TypeOfPoint.Re));
        //14
        vertexesOfShark.add(new Point(120, 71, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(150, 65, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(180, 60, TypeOfPoint.Re));
        //15
        vertexesOfShark.add(new Point(350, 80, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(375, 160, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(400, 240, TypeOfPoint.Re));
        //16
        vertexesOfShark.add(new Point(410, 250, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(410, 235, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(410, 220, TypeOfPoint.Re));
        //17
        vertexesOfShark.add(new Point(405, 100, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(357, 25, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(310, -50, TypeOfPoint.Re));
        //18
        vertexesOfShark.add(new Point(290, -100, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(330, -150, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(370, -200, TypeOfPoint.Re));
        //19
        vertexesOfShark.add(new Point(370, -230, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(325, -195, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(280, -160, TypeOfPoint.Re));
        //20
        vertexesOfShark.add(new Point(265, -158, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(237, -109, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(200, -60, TypeOfPoint.Re));
        //21
        vertexesOfShark.add(new Point(150, -70, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(127, -73, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(105, -77, TypeOfPoint.Re));
        //22
        vertexesOfShark.add(new Point(115, -90, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(117, -107, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(120, -125, TypeOfPoint.Re));
        //23
        vertexesOfShark.add(new Point(120, -160, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(90, -122, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(60, -84, TypeOfPoint.Re));
        //24
        vertexesOfShark.add(new Point(0, -95, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(20, -98, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-40, -102, TypeOfPoint.Re));
        //25
        vertexesOfShark.add(new Point(-15, -150, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-12, -156, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-10, -260, TypeOfPoint.Re));
        //26
        vertexesOfShark.add(new Point(-35, -290, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-87, -195, TypeOfPoint.Re));
        vertexesOfShark.add(new Point(-140, -100, TypeOfPoint.Re));
        return vertexesOfShark;
    }
}

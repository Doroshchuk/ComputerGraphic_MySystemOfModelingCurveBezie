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
    private ArrayList<Line> axisLines;
    private ArrayList<Line> gridLines;
    private ArrayList<Line> shapeLines;
    private double rotationAngle;

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public ArrayList<Line> getAxisLines() {
        return axisLines;
    }

    public void setAxisLines(ArrayList<Line> axisLines) {
        this.axisLines = axisLines;
    }

    public ArrayList<Line> getGridLines() {
        return gridLines;
    }

    public void setGridLines(ArrayList<Line> gridLines) {
        this.gridLines = gridLines;
    }

    public ArrayList<Line> getShapeLines() {
        return shapeLines;
    }

    public void setShapeLines(ArrayList<Line> shapeLines) {
        this.shapeLines = shapeLines;
    }

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
        gridLines = new ArrayList<>();
        axisLines = new ArrayList<>();
        shapeLines = new ArrayList<>();
        createLinesForCoordinateGrid();
        createPlot(typeOfPlot);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 13));
        switch (display) {
            case "Re":
                if (typeOfPlot.equals("surface")) {
                    graphics.drawString("zRe", (int) unitVectorSize, (int) (- getHeight() / 2 + unitVectorSize));
                    graphics.drawString("yRe", (int) (getWidth() / 2 - unitVectorSize * 1.5), - 15);
                    graphics.drawString("xRe", - getWidth() / 4 - 15, (int) (getHeight() / 4 - unitVectorSize));
                } else {
                    graphics.drawString("yRe", (int) unitVectorSize, -getHeight() / 2 + 15);
                    graphics.drawString("xRe", (int) (getWidth() / 2 - unitVectorSize * 1.5), -15);
                }
                break;
            case "Im":
                if (typeOfPlot.equals("surface")) {
                    graphics.drawString("zIm", (int) unitVectorSize, (int) (- getHeight() / 2 + unitVectorSize));
                    graphics.drawString("yIm", (int) (getWidth() / 2 - unitVectorSize * 1.5), - 15);
                    graphics.drawString("xIm", - getWidth() / 4 - 15, (int) (getHeight() / 4 - unitVectorSize));
                } else {
                    graphics.drawString("yIm", (int) unitVectorSize, -getHeight() / 2 + 15);
                    graphics.drawString("xIm", (int) (getWidth() / 2 - unitVectorSize * 1.5), -15);
                }
                break;
        }
    }

    public Plot() {
        unitVectorSize = 20;
        x0 = 0;
        y0 = 0;
        points = new ArrayList<>();
        vertexes = new ArrayList<>();
        gridLines = new ArrayList<>();
        axisLines = new ArrayList<>();
        shapeLines = new ArrayList<>();
        rotationAngle = 0;
    }

    private void createLinesForCoordinateGrid() {
        for (int i = 0; i < getWidth(); i += unitVectorSize) {
            //vertical lines
            gridLines.add(new Line(new Point(x0 - getWidth() / 2 + i, y0 + getHeight()), new Point(x0 - getWidth() / 2 + i, y0 - getHeight()), TypeOfLine.COORDINATEGridLine));
        }
        for (int i = 0; i < getHeight(); i += unitVectorSize) {
            //horizontal lines
            gridLines.add(new Line(new Point(x0 - getWidth(), y0 - getHeight() / 2 + i), new Point(x0 + getWidth(), y0 - getHeight() / 2 + i), TypeOfLine.COORDINATEGridLine));
        }
        gridLines.add(new Line(new Point(unitVectorSize, -2), new Point(unitVectorSize, 2), TypeOfLine.COORDINATEGridLine));
        gridLines.add(new Line(new Point(-2, unitVectorSize), new Point(2, unitVectorSize), TypeOfLine.COORDINATEGridLine));
    }

    private void createLinesForAxis2D() {
        //x and y lines
        axisLines.add(new Line(new Point(0, -getHeight() / 2), new Point(0, getHeight() / 2), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(-10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine));

        axisLines.add(new Line(new Point(-getWidth() / 2, 0), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(getWidth() / 2 - 10, -10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(getWidth() / 2 - 10, 10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 11));
        graphics.drawString("20", (int) unitVectorSize - 12, 10);
        graphics.drawString("20", 4, (int) -unitVectorSize);
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 15));
    }

    private void createLinesForAxis3D() {
        //x, y and z lines
        axisLines.add(new Line(new Point(0, 0), new Point (0, getHeight() / 2), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(- 10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(10, getHeight() / 2 - 10), new Point(0, getHeight() / 2), TypeOfLine.AXISLine));

        axisLines.add(new Line(new Point(0, 0), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(getWidth() / 2 - 10, - 10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(getWidth() / 2 - 10, + 10), new Point(getWidth() / 2, 0), TypeOfLine.AXISLine));

        axisLines.add(new Line(new Point(- getWidth() / 4, - getHeight() / 4), new Point(getWidth() / 4, getHeight() / 4), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(- getWidth() / 4, - getHeight() / 4 + 15), new Point(- getWidth() / 4, - getHeight() / 4), TypeOfLine.AXISLine));
        axisLines.add(new Line(new Point(- getWidth() / 4 + 15, - getHeight() / 4), new Point(- getWidth() / 4, - getHeight() / 4), TypeOfLine.AXISLine));
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
                graphics.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
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
                createLinesForAxis2D();
                PlotOfElementaryCurve plotOfElementaryCurve = new PlotOfElementaryCurve(this);
                plotOfElementaryCurve.calculateCurveLength(complexVertexes);
                plotOfElementaryCurve.draw(vertexes);
                drawLines(gridLines);
                drawLines(axisLines);
                drawLines(shapeLines);
                break;
            case "grid":
                createLinesForAxis2D();
                Grid grid = new Grid(this);
                grid.draw(complexVertexes);
                drawLines(gridLines);
                drawLines(axisLines);
                drawLines(shapeLines);
                break;
            case "surface":
                createLinesForAxis3D();
                Surface surface = new Surface(this);
                surface.draw(complexVertexes);
                drawLines(gridLines);
                drawLines(axisLines);
                drawLines(shapeLines);
                break;
        }
    }

    private void drawLines(ArrayList<Line> lines){
        for (Line line: lines){
            drawLine(line.getStartPoint(), line.getEndPoint(), line.getTypeOfLine());
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

    public void rotate(Point controlPoint, double angle, String axis){
        double alpha = - (angle * Math.PI / 180);
        ArrayList<ComplexPoint> rotatedComplexVertexes = new ArrayList<>();
        Point pointRe, pointIm;
        switch (axis){
            case "X":
                for (ComplexPoint complexPoint: complexVertexes){
                    pointRe = rotateCoordinateByX(complexPoint.getPointRe(), controlPoint, alpha);
                    pointIm = rotateCoordinateByX(complexPoint.getPointIm(), controlPoint, alpha);
                    rotatedComplexVertexes.add(new ComplexPoint(pointRe, pointIm));
                }
                break;
            case "Y":
                for (ComplexPoint complexPoint: complexVertexes){
                    pointRe = rotateCoordinateByY(complexPoint.getPointRe(), controlPoint, alpha);
                    pointIm = rotateCoordinateByY(complexPoint.getPointIm(), controlPoint, alpha);
                    rotatedComplexVertexes.add(new ComplexPoint(pointRe, pointIm));
                }
                break;
            case "Z":
                for (ComplexPoint complexPoint: complexVertexes){
                    pointRe = rotateCoordinateByZ(complexPoint.getPointRe(), controlPoint, alpha);
                    pointIm = rotateCoordinateByZ(complexPoint.getPointIm(), controlPoint, alpha);
                    rotatedComplexVertexes.add(new ComplexPoint(pointRe, pointIm));
                }
                break;
        }
        complexVertexes = rotatedComplexVertexes;
    }

    private Point rotateCoordinateByZ(Point currentPoint, Point controlPoint, double angle){
        double x = currentPoint.getX() * Math.cos(angle) - currentPoint.getY() * Math.sin(angle) - controlPoint.getX() * Math.cos(angle) + controlPoint.getY() * Math.sin(angle) + controlPoint.getX();
        double y = currentPoint.getX() * Math.sin(angle) + currentPoint.getY() * Math.cos(angle) - controlPoint.getX() * Math.sin(angle) - controlPoint.getY() * Math.cos(angle) + controlPoint.getY();
        double z = currentPoint.getZ();
        return new Point(x, y, z);
    }

    private Point rotateCoordinateByX(Point currentPoint, Point controlPoint, double angle){
        double x = currentPoint.getX();
        double y = currentPoint.getY() * Math.cos(angle) - currentPoint.getZ() * Math.sin(angle) - controlPoint.getY() * Math.cos(angle) + controlPoint.getZ() * Math.sin(angle) + controlPoint.getY();
        double z = currentPoint.getY() * Math.sin(angle) + currentPoint.getZ() * Math.cos(angle) - controlPoint.getY() * Math.sin(angle) - controlPoint.getZ() * Math.cos(angle) + controlPoint.getZ();
        return new Point(x, y, z);
    }

    private Point rotateCoordinateByY(Point currentPoint, Point controlPoint, double angle){
        double x = currentPoint.getX() * Math.cos(angle) + currentPoint.getZ() * Math.sin(angle) - controlPoint.getX() * Math.cos(angle) - controlPoint.getZ() * Math.sin(angle) + controlPoint.getX();
        double y = currentPoint.getY();
        double z = - currentPoint.getX() * Math.sin(angle) + currentPoint.getZ() * Math.cos(angle) + controlPoint.getX() * Math.sin(angle) - controlPoint.getZ() * Math.cos(angle) + controlPoint.getZ();
        return new Point(x, y, z);
    }

    public void move(float delta_x, float delta_y, float delta_z){
        ArrayList<ComplexPoint> movedComplexVertexes = new ArrayList<>();
        for (ComplexPoint complexPoint: complexVertexes){
            Point pointRe = moveCoordinate(complexPoint.getPointRe(), delta_x, delta_y, delta_z);
            Point pointIm = moveCoordinate(complexPoint.getPointIm(), delta_x, delta_y, delta_z);
            movedComplexVertexes.add(new ComplexPoint(pointRe, pointIm));
        }
        complexVertexes = movedComplexVertexes;
    }

    private Point moveCoordinate(Point point, float delta_x, float delta_y, float delta_z){
        return new Point(point.getX() + delta_x, point.getY() + delta_y, point.getZ() + delta_z);
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

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplexCurve extends ComplexLine {
    private ComplexPoint secondPoint;
    private ComplexPoint thirdPoint;
    private final double step = 0.1;

    public ComplexPoint getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(ComplexPoint secondPoint) {
        this.secondPoint = secondPoint;
    }

    public ComplexPoint getThirdPoint() {
        return thirdPoint;
    }

    public void setThirdPoint(ComplexPoint thirdPoint) {
        this.thirdPoint = thirdPoint;
    }

    public double getStep() {
        return step;
    }

    public ComplexCurve(ComplexPoint startPoint, ComplexPoint secondPoint, ComplexPoint thirdPoint, ComplexPoint endPoint, TypeOfLine typeOfLine) {
        super(startPoint, endPoint, typeOfLine);
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
    }

    public Map<Double, ArrayList<ComplexPoint>> calculatePointsOnTheCurves(TypeOfModeling typeOfModeling) {
        Map<Double, ArrayList<ComplexPoint>> curves =  new HashMap<>();
        ArrayList<ComplexPoint> pointsOfTheCurve;
        double key = 0;
        for (double v = 0; v <= 1; v += step) {
            pointsOfTheCurve = new ArrayList<>();
            for (double u = 0; u < 1 + step; u += step) {
                addNewComplexPoint(pointsOfTheCurve, typeOfModeling, u, v);
            }
            curves.put(key, pointsOfTheCurve);
            key++;
        }
        for (double u = 0; u <= 1; u += step) {
            pointsOfTheCurve = new ArrayList<>();
            for (double v = 0; v < 1 + step; v += step) {
                addNewComplexPoint(pointsOfTheCurve, typeOfModeling, u, v);
            }
            curves.put(key, pointsOfTheCurve);
            key++;
        }
        return curves;
    }

    private void addNewComplexPoint(ArrayList<ComplexPoint> pointsOfTheCurve, TypeOfModeling typeOfModeling, double v, double u){
        if (typeOfModeling.equals(TypeOfModeling.NORMAL))
            pointsOfTheCurve.add(calculateCoordinatesNewComplexPoint(v, u));
        else if(typeOfModeling.equals(TypeOfModeling.DIFFERENT)){
            pointsOfTheCurve.add(calculateCoordinatesNewComplexPoint(v, (u + Math.pow(v, 2))));
        }
    }

    private ComplexPoint calculateCoordinatesNewComplexPoint(double u, double v) {
        Complex t = new Complex(u, v);
        Complex tSquare = t.multiply(t);
        Complex tCube = t.multiply(t).multiply(t);

        Complex cubeComplexValue = (t.multiply(-1).add(1)).pow(3);
        Complex squareComplexValue = (t.multiply(-1).add(1)).pow(2);
        Complex complexValue = t.multiply(-1).add(1);

        Complex x0 = getStartPoint().getComplexX().multiply(cubeComplexValue);
        Complex x1 = getSecondPoint().getComplexX().multiply(3).multiply(squareComplexValue).multiply(t);
        Complex x2 = getThirdPoint().getComplexX().multiply(3).multiply(complexValue).multiply(tSquare);
        Complex x3 = getEndPoint().getComplexX().multiply(tCube);
        Complex x = x0.add(x1).add(x2).add(x3);

        Complex y0 = getStartPoint().getComplexY().multiply(cubeComplexValue);
        Complex y1 = getSecondPoint().getComplexY().multiply(3).multiply(squareComplexValue).multiply(t);
        Complex y2 = getThirdPoint().getComplexY().multiply(3).multiply(complexValue).multiply(tSquare);
        Complex y3 = getEndPoint().getComplexY().multiply(tCube);
        Complex y = y0.add(y1).add(y2).add(y3);
        return new ComplexPoint(new Point(x.getReal(), y.getReal()), new Point(x.getImaginary(), y.getImaginary()));
    }

    public ArrayList<ComplexLine> calculateLinesOnTheCurve(TypeOfModeling typeOfModeling){
        ArrayList<ComplexLine> linesOfTheCurve = new ArrayList<>();
        Map<Double, ArrayList<ComplexPoint>> curves = calculatePointsOnTheCurves(typeOfModeling);
        TypeOfLine typeOfLine;
        for (double key: curves.keySet()){
            if (key == 0) typeOfLine = TypeOfLine.COMPLEXCurveLine;
            else typeOfLine = TypeOfLine.CURVELine;
            for (int i = 0; i < curves.get(key).size() - 2; i++){
                linesOfTheCurve.add(new ComplexLine(curves.get(key).get(i), curves.get(key).get(i + 1), typeOfLine));
            }
        }
        return linesOfTheCurve;
    }
}

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class PlotOfElementaryCurve {
    private Plot plot;

    public PlotOfElementaryCurve(Plot plot){
        this.plot = plot;
    }

    private ArrayList<Line> createLinesForCurve(Curve curve){
        plot.setShapeLines(new ArrayList<>());
        ArrayList<Line> lines = curve.calculateLinesOnTheCurve();
        return lines;
    }

    public void draw(ArrayList<Point> points){
        plot.setComplexVertexes(new ArrayList<>());
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size() - 3; i += 3) {
            lines.addAll(createLinesForCurve(new Curve(points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3), TypeOfLine.CURVELine)));
        }
        plot.setShapeLines(lines);
        plot.initReImVertexes();
    }

    public void calculateCurveLength(ArrayList<ComplexPoint> complexVertexes) {
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
}

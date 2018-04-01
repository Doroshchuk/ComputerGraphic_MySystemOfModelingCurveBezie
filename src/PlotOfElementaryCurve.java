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
}

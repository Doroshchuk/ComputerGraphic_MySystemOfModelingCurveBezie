import java.util.ArrayList;

public class Surface {
    private Plot plot;

    public Surface(Plot plot){
        this.plot = plot;
    }

    private Point executeCavalierProjection(Point point){
        double x = point.getX() + point.getZ() * Math.cos(Math.PI / 4);
        double y = point.getY() + point.getZ() * Math.sin(Math.PI / 4);
        return new Point(x, y, 0);
    }

    public ArrayList<Line> transformLines(ArrayList<Line> lines){
        ArrayList<Line> transformedLines = new ArrayList<>();
        for(Line line: lines){
            transformedLines.add(new Line(executeCavalierProjection(line.getStartPoint()), executeCavalierProjection(line.getEndPoint()), line.getTypeOfLine()));
        }
        return transformedLines;
    }

    private void drawCurve(ComplexCurve complexCurve){
        ArrayList<ComplexLine> complexLines = complexCurve.calculateLinesOnTheCurve();
        ArrayList<Line> lines = new ArrayList<>();
        for (ComplexLine complexLine : complexLines) {
            switch (plot.getDisplay()){
                case "Re":
                    lines.add(new Line(complexLine.getStartPoint().getPointRe(), complexLine.getEndPoint().getPointRe(), complexLine.getTypeOfLine()));
                    break;
                case "Im":
                    lines.add(new Line(complexLine.getStartPoint().getPointIm(), complexLine.getEndPoint().getPointIm(), complexLine.getTypeOfLine()));
                    break;
            }
        }
        lines = transformLines(lines);
        for (Line line: lines) {
            plot.drawLine(line.getStartPoint(), line.getEndPoint(), line.getTypeOfLine());
        }
    }

    public void draw(ArrayList<ComplexPoint> complexVertexes){
        ArrayList<ComplexPoint> initComplexPoints = new ArrayList<>();
        initComplexPoints.addAll(complexVertexes);
        for (int i = 0; i < initComplexPoints.size() - 3; i += 3) {
            drawCurve(new ComplexCurve(initComplexPoints.get(i), initComplexPoints.get(i + 1), initComplexPoints.get(i + 2), initComplexPoints.get(i + 3), TypeOfLine.CURVELine));
        }
    }
}
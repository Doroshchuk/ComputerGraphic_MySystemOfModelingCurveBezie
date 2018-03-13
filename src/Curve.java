import java.util.ArrayList;

public class Curve extends Line {
    private Point secondPoint;
    private Point thirdPoint;
    private final double u = 0.1;

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
    }

    public Point getThirdPoint() {
        return thirdPoint;
    }

    public void setThirdPoint(Point thirdPoint) {
        this.thirdPoint = thirdPoint;
    }

    public double getU() {
        return u;
    }

    public Curve(Point startPoint, Point secondPoint, Point thirdPoint, Point endPoint, TypeOfLine typeOfLine) {
        super(startPoint, endPoint, typeOfLine);
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
    }

    private ArrayList<Point> calculatePointsOnTheCurve(){
        double x, y;
        ArrayList<Point> pointsOfTheCurve = new ArrayList<>();
        for (double t = 0; t < 1 + u; t += u){
            x = getStartPoint().getX() * Math.pow(1 - t, 3) + 3 * getSecondPoint().getX() * Math.pow(1 - t, 2) * t + 3 * getThirdPoint().getX() * (1 - t) * Math.pow(t, 2) + getEndPoint().getX() * Math.pow(t, 3);
            y = getStartPoint().getY() * Math.pow(1 - t, 3) + 3 * getSecondPoint().getY() * Math.pow(1 - t, 2) * t + 3 * getThirdPoint().getY() * (1 - t) * Math.pow(t, 2) + getEndPoint().getY() * Math.pow(t, 3);
            pointsOfTheCurve.add(new Point(x, y));
        }
        return pointsOfTheCurve;
    }

    public ArrayList<Line> calculateLinesOnTheCurve(){
        ArrayList<Line> linesOfTheCurve = new ArrayList<>();
        ArrayList<Point> pointsOfTheCurve = calculatePointsOnTheCurve();
        for (int i = 0; i < pointsOfTheCurve.size() - 2; i++){
            linesOfTheCurve.add(new Line(pointsOfTheCurve.get(i), pointsOfTheCurve.get(i + 1), TypeOfLine.CURVELine));
        }
        return linesOfTheCurve;
    }
}

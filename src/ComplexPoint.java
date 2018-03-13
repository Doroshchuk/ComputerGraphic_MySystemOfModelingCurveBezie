import org.apache.commons.math3.complex.Complex;

public class ComplexPoint {
    private Point pointRe;
    private Point pointIm;

    public ComplexPoint(Point pointRe, Point pointIm){
        this.pointRe = pointRe;
        this.pointRe.setTypeOfPoint(TypeOfPoint.Re);
        this.pointIm = pointIm;
        this.pointIm.setTypeOfPoint(TypeOfPoint.Im);
    }

    public Point getPointRe() {
        return pointRe;
    }

    public void setPointRe(Point pointRe) {
        this.pointRe = pointRe;
    }

    public Point getPointIm() {
        return pointIm;
    }

    public void setPointIm(Point pointIm) {
        this.pointIm = pointIm;
    }

    public Complex getComplexX(){
        return new Complex(pointRe.getX(), pointIm.getX());
    }

    public Complex getComplexY(){
        return new Complex(pointRe.getY(), pointIm.getY());
    }
}

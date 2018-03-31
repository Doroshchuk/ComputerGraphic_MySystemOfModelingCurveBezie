public class Point {
    private double x;
    private double y;
    private double z;
    private TypeOfPoint typeOfPoint;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y, TypeOfPoint typeOfPoint){
        this.x = x;
        this.y = y;
        this.typeOfPoint = typeOfPoint;
    }

    public Point(double x, double y, double z, TypeOfPoint typeOfPoint){
        this.x = x;
        this.y = y;
        this.z = z;
        this.typeOfPoint = typeOfPoint;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public TypeOfPoint getTypeOfPoint() {
        return typeOfPoint;
    }

    public void setTypeOfPoint(TypeOfPoint typeOfPoint) {
        this.typeOfPoint = typeOfPoint;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Point) {
            sameSame = this.x == ((Point) object).x && this.y == ((Point) object).y && this.typeOfPoint == ((Point) object).typeOfPoint;
        }

        return sameSame;
    }
}

package model;

public class Point {

    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double X(){
        return x;
    }

    public double Y(){
        return y;
    }

    public double Z(){
        return z;
    }

    public Point rotate(Quaternion q){
        Quaternion pointQ = new Quaternion(0, x, y, z);
        Quaternion resultQ = q.multiply(pointQ).multiply(q.inverse().normalize());
        return new Point(resultQ.X(), resultQ.Y(), resultQ.Z());
    }

    public Point interpolatePoint(double part){
        double X = part * x / (1 + part);
        double Y = part * y / (1 + part);
        double Z = part * z / (1 + part);

        return new Point(X, Y, Z);
    }

    public Quaternion toQuaternion(){
        return new Quaternion(0, x, y, z);
    }

}

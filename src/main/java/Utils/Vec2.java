package Utils;


public class Vec2 {
    public double x, y;

    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Vec2 p){ return Math.sqrt(distanceSquared(p)); }

    public double distanceSquared(Vec2 p){
        return (this.x - p.x)*(this.x - p.x) + (this.y - p.y)*(this.y - p.y);
    }

    public static double[][] toCoordinates(Vec2[] arr){
        double[][] res = new double[arr.length][2];
        for (int i = 0 ; i < arr.length ; i ++ ){
            res[i][0] = arr[i].x;
            res[i][1] = arr[i].y;
        }
        return res;
    }

    public Vec2 add(Vec2 v){
        return new Vec2(this.x + v.x, this.y + v.y);
    }

    public Vec2 mult(double s){
        return new Vec2(x * s, y * s);
    }

    public double normSquared(){
        return distanceSquared(new Vec2(0, 0));
    }

    public double norm(){
        return Math.sqrt(normSquared());
    }
}

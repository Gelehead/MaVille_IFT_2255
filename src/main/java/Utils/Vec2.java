package Utils;


public class Vec2 {
    public int x, y;

    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double distance(Vec2 p){ return Math.sqrt(distanceSquared(p)); }

    public double distanceSquared(Vec2 p){
        return (this.x - p.x)*(this.x - p.x) + (this.y - p.y)*(this.y - p.y);
    }

    public static int[][] toCoordinates(Vec2[] arr){
        int[][] res = new int[arr.length][2];
        for (int i = 0 ; i < arr.length ; i ++ ){
            res[i][0] = arr[i].x;
            res[i][1] = arr[i].y;
        }
        return res;
    }
}

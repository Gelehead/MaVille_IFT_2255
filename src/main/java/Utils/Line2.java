package Utils;

public class Line2 {
    public Vec2 p1, p2;
    public Line2(Vec2 p1, Vec2 p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public double norm(){return Math.sqrt(normSquared());}
    
    // returns norm Squared( p1 - p2 )
    public double normSquared(){return p1.add(this.p2.mult(-1)).normSquared();}
}

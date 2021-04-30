import java.awt.*;
public class Triangle extends Item {
  private Point point1;
  private Point point2;
  private Point point3;
  public Triangle(Point point1, Point point2, Point point3) {
    this.point1 = point1;
    this.point2 = point2;
    this.point3 = point3;
  }

  public Triangle(Point point1) {
    this.point1 = point1;
	point2 = null;
    point3 = null;
  }
  public Triangle() {
	  point1 = null;
	  point2 = null;
      point3 = null;
  }
  public Triangle(Point p1, Point p2) {
    this.point1 = p1;
    this.point2 = p2;
    this.point3 = null;
  }

  public void translate(int x, int y) {
    point1.setLocation(point1.getX() + x, point1.getY() + y);
    point2.setLocation(point2.getX() + x, point2.getY() + y);
    point3.setLocation(point3.getX() + x, point3.getY() + y);
  }

  public boolean includes(Point point) {
    return ((distance(point, point1 ) < 10.0) || (distance(point, point2)< 10.0) || distance(point, point3) < 10.0);
  }

  public void render() {
    uiContext.drawLine(point1, point2);
    uiContext.drawLine(point2, point3);
    uiContext.drawLine(point3, point1);
  }
  public void setPoint1(Point point) {
    point1 = point;
  }
  public void setPoint2(Point point) {
    point2 = point;
  }
  public void setPoint3(Point p) {
    point3 = p;
  }
  public Point getPoint1() {
    return point1;
  }
  public Point getPoint2() {
    return point2;
  }
  public Point getPoint3() {
    return point3;
  }
  public String toString() {
    return "Triangle  from " + point1 + " to " + point2 + " to " + point3;
  }
}


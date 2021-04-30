import java.awt.*;
import java.text.*;
public class TriangleCommand extends Command {
  private Triangle triangle;
  private int pointCount;
  public TriangleCommand() {
    this(null, null);
    pointCount = 0;
  }
  public TriangleCommand(Point point) {
    this(point, null);
    pointCount = 1;
  }
  public TriangleCommand(Point point1, Point point2) {
    triangle = new Triangle(point1, point2);
    pointCount = 2;
  }
  public TriangleCommand(Point p1, Point p2, Point p3) {
    triangle = new Triangle(p1, p2, p3);
    pointCount = 3;
  }
  public void setTrianglePoint(Point point) {
    if (++pointCount == 1) {
      triangle.setPoint1(point);
    } else if (pointCount == 2) {
      triangle.setPoint2(point);
    } else if (pointCount == 3) {
      triangle.setPoint3(point);
    }
  }
  public void execute() {
    model.addItem(triangle);
  }
  public boolean undo() {
    model.removeItem(triangle);
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }
  public boolean end() {
    if (triangle.getPoint1() == null) {
      undo();
      return false;
    }

    if (triangle.getPoint2() == null) {
       triangle.setPoint2(triangle.getPoint1());
    }

    if (triangle.getPoint3() == null) {
       triangle.setPoint3(triangle.getPoint2());
    }
    return true;
  }
}

class Point {
  double x;
  double y;

  Point(this.x, this.y);
}

class Rectangle {
  Point topLeft;
  Point bottomRight;

  Rectangle(this.topLeft, this.bottomRight);

  double getArea() {
    double width = bottomRight.x - topLeft.x;
    double height = topLeft.y - bottomRight.y;
    return width * height;
  }
}

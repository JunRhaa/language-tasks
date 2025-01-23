abstract class Transport {
  void move();
}

class Car extends Transport {
  @override
  void move() {
    print('Car is driving');
  }
}

class Bike extends Transport {
  @override
  void move() {
    print('Bike is cycling');
  }
}

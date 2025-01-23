class Animal {
  void move() {}
}

class Fish extends Animal {
  @override
  void move() {
    print('Fish swims');
  }
}

class Bird extends Animal {
  @override
  void move() {
    print('Bird flies');
  }
}

class Dog extends Animal {
  @override
  void move() {
    print('Dog runs');
  }
}

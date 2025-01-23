abstract class Animal {
  void makeSound();
}

class Dog implements Animal {
  @override
  void makeSound() {
    print('Woof!');
  }
}

class Cat implements Animal {
  @override
  void makeSound() {
    print('Meow!');
  }
}

class Cow implements Animal {
  @override
  void makeSound() {
    print('Moo!');
  }
}

void demonstratePolymorphism() {
  List<Animal> animals = [Dog(), Cat(), Cow()];
  for (var animal in animals) {
    animal.makeSound();
  }
}

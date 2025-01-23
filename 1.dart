import 'dart:io';

class Person {
  String name;
  int age;
  String gender;

  Person(this.name, this.age, this.gender);

  void displayInfo() {
    print('Name: $name, Age: $age, Gender: $gender');
  }

  void increaseAge() {
    age++;
  }

  void changeName(String newName) {
    name = newName;
  }
}

void main() {
  print("Enter name:");
  String name = stdin.readLineSync() ?? '';
  print("Enter age:");
  int age = int.parse(stdin.readLineSync() ?? '0');
  print("Enter gender:");
  String gender = stdin.readLineSync() ?? '';

  Person person = Person(name, age, gender);
  person.displayInfo();

  person.increaseAge();
  print("After a year:");
  person.displayInfo();
}

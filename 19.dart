class Device {
  String brand;

  Device(this.brand);

  void turnOn() {
    print('$brand device is turned on.');
  }

  void turnOff() {
    print('$brand device is turned off.');
  }
}

class Smartphone extends Device {
  Smartphone(String brand) : super(brand);

  void takePhoto() {
    print('$brand smartphone takes a photo.');
  }
}

class Laptop extends Device {
  Laptop(String brand) : super(brand);

  void code() {
    print('$brand laptop is coding.');
  }
}

class Counter {
  static int _count = 0;

  Counter() {
    _count++;
  }

  static int get count => _count;
}

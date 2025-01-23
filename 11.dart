class Product {
  String name;
  double price;
  int quantity;

  Product(this.name, this.price, this.quantity);
}

class Store {
  List<Product> products = [];

  void addProduct(Product product) {
    products.add(product);
  }

  void removeProduct(String name) {
    products.removeWhere((product) => product.name == name);
  }

  Product? findProduct(String name) {
    return products.firstWhere((product) => product.name == name, orElse: () => null);
  }
}

class Product {
  String name;
  double price;

  Product(this.name, this.price);
}

class Customer {
  String name;

  Customer(this.name);
}

class Order {
  Customer customer;
  List<Product> products = [];

  Order(this.customer);

  void addProduct(Product product) {
    products.add(product);
  }

  double get totalCost {
    return products.fold(0, (total, product) => total + product.price);
  }

  void displayOrder() {
    print('Order for ${customer.name}:');
    for (var product in products) {
      print('- ${product.name}: \$${product.price}');
    }
    print('Total cost: \$${totalCost}');
  }
}

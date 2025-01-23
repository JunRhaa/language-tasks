class Book {
  String title;
  String author;
  int year;

  Book(this.title, this.author, this.year);
}

class Library {
  List<Book> books = [];

  void addBook(Book book) {
    books.add(book);
  }

  List<Book> findByAuthor(String author) {
    return books.where((book) => book.author == author).toList();
  }

  List<Book> findByYear(int year) {
    return books.where((book) => book.year == year).toList();
  }
}

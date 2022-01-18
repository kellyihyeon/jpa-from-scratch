package jpa.jpashop.domain.item;

public class PrintVisitor implements Visitor {

    @Override
    public void visit(Book book) {      // book = 원본 엔티티
        System.out.println("book.getClass() = " + book.getClass());
        System.out.println("[PrintVisitor]  [ 제목:" + book.getName() + ", 저자: " + book.getAuthor() + " ]");
    }

    @Override
    public void visit(Album album) {
        System.out.println("album.getClass() = " + album.getClass());
        System.out.println("[PrintVisitor]  [ 제목:" + album.getName() + ", 가수: " + album.getArtist() + " ]");
    }

    @Override
    public void visit(Movie movie) {
        System.out.println("movie.getClass() = " + movie.getClass());
        System.out.println("[PrintVisitor]  [ 제목:" + movie.getName() + ", 감독: " + movie.getDirector() + " ]");
    }
}

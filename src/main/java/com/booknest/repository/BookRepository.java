package com.booknest.repository;

import com.booknest.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.deleted = false")
    List<Book> findAllActive();

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.featured = true")
    List<Book> findFeaturedBooks();

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.bestSeller = true")
    List<Book> findBestSellers();

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.newArrival = true")
    List<Book> findNewArrivals();

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.category.id = :categoryId")
    List<Book> findByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.author.id = :authorId")
    List<Book> findByAuthor(@Param("authorId") Long authorId);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND " +
           "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Book> searchBooks(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.category.id = :categoryId AND " +
           "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Book> searchBooksByCategory(@Param("categoryId") Long categoryId, 
                                     @Param("keyword") String keyword, 
                                     Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                 @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.rating >= :rating")
    List<Book> findByMinRating(@Param("rating") BigDecimal rating);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.stock < :threshold")
    List<Book> findLowStockBooks(@Param("threshold") Integer threshold);

    @Query("SELECT b FROM Book b WHERE b.deleted = false ORDER BY b.soldCount DESC")
    List<Book> findTopSellingBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false ORDER BY b.viewCount DESC")
    List<Book> findMostViewedBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.deleted = false AND b.stock > 0")
    List<Book> findInStockBooks();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.category.id = :categoryId AND b.deleted = false")
    Long countByCategory(@Param("categoryId") Long categoryId);
}

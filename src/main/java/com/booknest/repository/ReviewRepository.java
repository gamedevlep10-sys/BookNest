package com.booknest.repository;

import com.booknest.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Review entity
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId AND r.approved = true")
    List<Review> findApprovedReviewsByBookId(@Param("bookId") Long bookId);

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId AND r.approved = true")
    Page<Review> findApprovedReviewsByBookId(@Param("bookId") Long bookId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.approved = false")
    List<Review> findPendingReviews();

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.id = :bookId AND r.approved = true")
    Double getAverageRatingByBookId(@Param("bookId") Long bookId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.book.id = :bookId AND r.approved = true")
    Long countApprovedReviewsByBookId(@Param("bookId") Long bookId);
}

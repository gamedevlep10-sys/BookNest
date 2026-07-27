package com.booknest.repository;

import com.booknest.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Wishlist entity
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUserIdAndBookId(Long userId, Long bookId);

    List<Wishlist> findByUserId(Long userId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    Boolean existsByUserIdAndBookId(Long userId, Long bookId);
}

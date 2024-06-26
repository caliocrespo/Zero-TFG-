package com.zero.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zero.domain.Progress;
import com.zero.domain.Review;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Integer>{
	@Query("select p from Progress p where p.game.id = ?1")
	Collection<Progress> findByGame(int gameId);
	
	@Query("select AVG(p.rating) as rating from Progress p where p.game.id = ?1")
	double findRatingByGame(int gameId); 
	
	@Query("select p from Progress p where p.game.id = ?1 AND p.user.username = ?2")
	Progress findByGameAndUser(int gameId, String username);
	
	@Query("select p from Progress p where p.id = ?1")
	Progress findById(int progressId);
	
	@Query("select p from Progress p where p.user.username = ?1")
	Collection<Progress> findByUsername(String username);
	
	@Query("select p from Progress p where p.user.username = ?1")
	Page<Progress> findByUser(String username, Pageable pageable); 
	
	@Query("select p from Progress p where p.user.username =?1 AND p.status = ?2")
	Page<Progress> findByUserAndStatus(String username, String status, Pageable pageable);
	
	@Query("select p from Progress p where p.status = 'Completed' AND p.user.username = ?1 ORDER BY p.finish_date DESC")
	Collection<Progress> findTop4CompletedByUser(String username);
	
	@Query("select p from Progress p where p.review.id = (select max(p2.review.id) from Progress p2 where p2.user.username = ?1)")
	Progress findLastProgress(String username);
	
	@Query("select p from Progress p  where p.user.username = ?1 ORDER BY p.rating DESC")
	Collection<Progress> findOrderProgress(String username);
	
	@Query("select p from Progress p where p.review IS NOT NULL AND p.game.id = ?1")
	Collection<Progress> findWithReview(int gameId);
	
	@Query("select p from Progress p where p.review.id = (select max(p2.review.id) from Progress p2 where p2.user.username = ?1)")
	Progress findLastReview(String username);
	
	@Query( value ="select p from Progress p where p.game.id = :gameId AND p.review.text IS NOT NULL")
	Page<Progress> findByGameReviewedPage(@Param("gameId") int gameId, Pageable pageable);
	
	@Query("select p from Progress p")
	List<Progress> findAll();
}

package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>  {
        
   //Query Practise
    @Query("select a from Message a where a.postedBy = :postedBy")
    List<Message> findAllByQuery(@Param("postedBy") Integer postedBy);

    List<Message> findAllByPostedBy(Integer accountId);

    //another testing 
    List<Message> findAllByPostedByOrderByMessageIdDesc(Integer accountId);    
}

package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{

    @Modifying
    @Transactional
    @Query(value="DELETE FROM todo WHERE completed=true", nativeQuery = true)
    void removeCompleted();

    @Modifying
    @Transactional
    @Query(value="UPDATE todo SET completed=?1", nativeQuery = true)
    void updateStatus(boolean completed);

    @Modifying
    @Transactional
    @Query(value="UPDATE todo SET completed=?1 WHERE id=?2", nativeQuery = true)
    void updateStatusById(boolean completed, int id);

    @Modifying
    @Transactional
    @Query(value="UPDATE todo SET title=?1 WHERE id=?2", nativeQuery = true)
    void updateTitleById(String title, int id);

    @Query(value="SELECT * FROM todo WHERE completed=?1", nativeQuery = true)
    List<Todo> findAllByCompleted(boolean completed);
}

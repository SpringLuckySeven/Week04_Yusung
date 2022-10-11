package com.sparta.prac01.repository;

import com.sparta.prac01.model.Folder;
import com.sparta.prac01.model.Folder;
import com.sparta.prac01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}

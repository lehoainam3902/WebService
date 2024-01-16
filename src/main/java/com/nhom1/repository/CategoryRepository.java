package com.nhom1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.nhom1.model.Category;

@RestController
@RequestMapping("/api/categories")

public interface CategoryRepository extends JpaRepository<Category, Long>{
	public Category findByName(String name);
	
//	@Query ("Select c from Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
//	public Category findByNameAndParant(@Param("name")String name, 
//			@Param("parentCategoryName")String parantCategoryName);
	@Query("SELECT c FROM Category c WHERE c.level = 3")
	@GetMapping("/findAllLevel3")
	List<Category> findAllLevel3();


}

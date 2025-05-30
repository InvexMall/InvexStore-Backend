package com.wnsud9771.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wnsud9771.dto.MainCategoryList;
import com.wnsud9771.entity.product.MainCategory;
import com.wnsud9771.entity.product.SubCategory;
import com.wnsud9771.repository.MainCategoryRepository;
import com.wnsud9771.repository.SubCategoryRepsotiroy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final MainCategoryRepository mainCategoryRepository;
	private final SubCategoryRepsotiroy subCategoryRepsotiroy;

//	@PostConstruct
//	@Transactional
//	public void init() {
//		try {
//			System.out.println("테스트용 임시 시작");
//			saveMainSubCategory("아우터", "패딩");
//		} catch (Exception e) {
//			System.out.println(" " + e.getMessage());
//		}
//	}

	// 카테고리 저장(main+sub 카테고리 한번에 저장)
	public void saveMainSubCategory(String maincategory, String subcategory) {

		MainCategory mainCategory = new MainCategory();
		mainCategory.setCategoryName(maincategory);
		mainCategory.setSubCategorylist(null);

		SubCategory subCategory = new SubCategory();
		subCategory.setCategoryName(subcategory);
		subCategory.setMainCategory(mainCategory);

		List<SubCategory> subCategoryList = new ArrayList<>();
		subCategoryList.add(subCategory);
		mainCategory.setSubCategorylist(subCategoryList);

		mainCategoryRepository.save(mainCategory);

	}

	
	//메인 카테고리리스트 뽑기
	public  List<MainCategoryList> searchMainCategoryList() {
		List<MainCategory> mainCategorys = mainCategoryRepository.findAll();

		return mainCategorys.stream().map(this::maincategoryconvertToDTO).collect(Collectors.toList());
	}
	
	private MainCategoryList maincategoryconvertToDTO(MainCategory entity) {
		MainCategoryList dto = new MainCategoryList();
		dto.setId(entity.getId());
		dto.setCategoryName(entity.getCategoryName());
		
		return dto;
	}
	
}










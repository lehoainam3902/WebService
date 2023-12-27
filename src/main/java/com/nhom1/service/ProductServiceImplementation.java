package com.nhom1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Category;
import com.nhom1.model.Product;
import com.nhom1.repository.CategoryRepository;
import com.nhom1.repository.ProductRepository;
import com.nhom1.request.CreateProductRequest;

// Lớp triển khai (implementation) của ProductService
@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    // Constructor chấp nhận các repository cần thiết
    public ProductServiceImplementation(ProductRepository productRepository, UserService userService,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    // Phương thức tạo mới sản phẩm dựa trên yêu cầu CreateProductRequest
    @Override
    public Product createProduct(CreateProductRequest req) {

        // Xác định danh mục cấp 1 (topLevel)
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        // Nếu không tìm thấy, tạo mới
        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        // Xác định danh mục cấp 2 (secondLevel)
        Category secondLevel = categoryRepository.findByNameAndParant(req.getSecondLevelCategory(), topLevel.getName());

        // Nếu không tìm thấy, tạo mới
        if (secondLevel == null) {

            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        // Xác định danh mục cấp 3 (thirdLevel)
        Category thirdLevel = categoryRepository.findByNameAndParant(req.getThirdLevelCategory(), secondLevel.getName());

        // Nếu không tìm thấy, tạo mới
        if (thirdLevel == null) {

            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        // Tạo mới sản phẩm
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSizes());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        // Lưu sản phẩm vào cơ sở dữ liệu
        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    // Phương thức xóa sản phẩm dựa trên ID
    @Override
    public String deleteProduct(Long productId) throws ProductException {
        // Tìm kiếm sản phẩm theo ID
        Product product = findProductById(productId);

        // Xóa thông tin về kích thước của sản phẩm
        product.getSizes().clear();

        // Xóa sản phẩm khỏi cơ sở dữ liệu
        productRepository.delete(product);

        return "Xóa thành công sản phẩm";
    }

    // Phương thức cập nhật thông tin sản phẩm dựa trên ID và yêu cầu Product
    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {

        // Tìm kiếm sản phẩm theo ID
        Product product = findProductById(productId);

        // Nếu có thông tin về số lượng mới, cập nhật
        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        return productRepository.save(product);
    }

    // Phương thức tìm kiếm sản phẩm dựa trên ID
    @Override
    public Product findProductById(Long id) throws ProductException {
        // Tìm kiếm sản phẩm theo ID trong cơ sở dữ liệu
        Optional<Product> opt = productRepository.findById(id);

        // Nếu tìm thấy, trả về sản phẩm, ngược lại ném ngoại lệ ProductException
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Không tồn tại sản phẩm có mã - " + id);
    }

    // Phương thức tìm kiếm sản phẩm theo danh mục
    @Override
    public List<Product> findProductByCategory(String category) {
        // TODO: Implement logic to find products by category
        return null;
    }

    // Phương thức lấy danh sách sản phẩm được lọc dựa trên nhiều tiêu chí
    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        // Tạo đối tượng Pageable để phân trang
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Lấy danh sách sản phẩm được lọc dựa trên các tiêu chí
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        // Lọc theo màu sắc nếu có
        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        // Lọc theo tình trạng tồn kho
        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        // Tính toán chỉ mục bắt đầu và kết thúc của trang
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        // Lấy danh sách sản phẩm cho trang hiện tại
        List<Product> pageContent = products.subList(startIndex, endIndex);

        // Tạo đối tượng Page chứa thông tin về trang và danh sách sản phẩm
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProducts;
    }

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}
}

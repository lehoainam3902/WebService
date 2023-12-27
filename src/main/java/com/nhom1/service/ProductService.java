package com.nhom1.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Product;
import com.nhom1.request.CreateProductRequest;

public interface ProductService {

    // Phương thức tạo mới sản phẩm dựa trên yêu cầu CreateProductRequest
    public Product createProduct(CreateProductRequest req);

    // Phương thức xóa sản phẩm dựa trên ID và ném ngoại lệ ProductException nếu có lỗi
    public String deleteProduct(Long productId) throws ProductException;

    // Phương thức cập nhật thông tin sản phẩm dựa trên ID, yêu cầu Product và ném ngoại lệ ProductException nếu có lỗi
    public Product updateProduct(Long productId, Product req) throws ProductException;

    // Phương thức tìm kiếm sản phẩm dựa trên ID và ném ngoại lệ ProductException nếu không tìm thấy
    public Product findProductById(Long id) throws ProductException;

    // Phương thức tìm kiếm sản phẩm dựa trên danh mục
    public List<Product> findProductByCategory(String category);

    // Phương thức lấy danh sách sản phẩm dựa trên nhiều tiêu chí và phân trang
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

	public List<Product> findAllProducts();
}

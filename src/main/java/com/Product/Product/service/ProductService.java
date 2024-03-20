package com.Product.Product.service;

import com.Product.Product.exceptions.ProductNotFoundException;
import com.Product.Product.models.Product;
import com.Product.Product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }
    public Product getProductById(long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + "not found"));
    }
    public Product getProductByName(String name){
        Product product = productRepository.findByName(name);
        if(product == null){
            throw new ProductNotFoundException("Product with name "+ name + "not found");
        }
        return product;
    }
    public Product updateProduct(Product product){
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow(()-> new ProductNotFoundException("PRODUCT WITH ID "+ product.getId() + "not found"));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        return productRepository.save(existingProduct);
    }
    public Product partialProductUpdate(long id, Product product){
        Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product with id " + id + "not found"));

        BeanUtils.copyProperties(product, existingProduct);
        return productRepository.save(existingProduct);
    }
//    private String[] getNullPropertyNames(Object source) {
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//
//        Set<String> emptyNames = new HashSet<>();
//        for (java.beans.PropertyDescriptor pd : pds) {
//            if (!pd.getName().equals("id")) { // Exclude the identifier
//                Object srcValue = src.getPropertyValue(pd.getName());
//                if (srcValue == null) {
//                    emptyNames.add(pd.getName());
//                }
//            }
//        }
//
//        String[] result = new String[emptyNames.size()];
//        return emptyNames.toArray(result);
//    }
    public String deleteProduct(long id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product with id " + id + "not found");
        }
        productRepository.deleteById(id);
        return "product deleted successfully";
    }
}

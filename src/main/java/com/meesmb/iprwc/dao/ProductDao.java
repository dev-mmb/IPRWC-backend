package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.FilterTag;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.repository.FilterTagRepository;
import com.meesmb.iprwc.repository.ProductRepository;
import com.meesmb.iprwc.request_objects.ProductRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class ProductDao {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    FilterTagRepository filterTagRepository;

    public HTTPResponse<List<Product>> getAllProducts() {
        return HTTPResponse.returnSuccess(productRepository.findAll());

    }

    public HTTPResponse<Product[]> addProducts(ProductRequestObject[] requestObjects) {
        Product[] returnValues = new Product[requestObjects.length];

        for (int i = 0; i < requestObjects.length; i++) {
            HTTPResponse<Product> response = addProduct(requestObjects[i]);

            if (!response.isSuccess())
                return HTTPResponse.<Product[]>returnFailure(response.getErrorMessage());

            returnValues[i] = response.getData();
        }

        return HTTPResponse.<Product[]>returnSuccess(returnValues);
    }

    public HTTPResponse<Product> addProduct(ProductRequestObject obj) {
        List<String> tagIds = Arrays.asList(obj.getFilterTags());
        List<FilterTag> tags = filterTagRepository.findAllById(tagIds);

        Product p = new Product(obj.getName(), obj.getPrice(), obj.getDescription(), obj.getSpecs(), tags, obj.getImage());
        productRepository.save(p);

        return HTTPResponse.<Product>returnSuccess(p);
    }

}

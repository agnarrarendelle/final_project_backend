package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.entity.Product;
import practice.entity.ProductComment;
import practice.entity.ProductParam;
import practice.mapper.ProductCommentMapper;
import practice.mapper.ProductMapper;
import practice.mapper.ProductParamMapper;
import practice.repository.ProductCommentRepository;
import practice.repository.ProductParamRepository;
import practice.repository.ProductRepository;
import practice.service.ProductService;
import practice.utils.PageHelper;
import practice.vo.product.ProductVo;
import practice.vo.productcomment.ProductCommentCounterVo;
import practice.vo.productcomment.ProductCommentVo;
import practice.vo.productparam.ProductParamVo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductParamRepository productParamRepository;

    @Autowired
    ProductCommentRepository productCommentRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductParamMapper productParamMapper;

    @Autowired
    ProductCommentMapper productCommentMapper;

    @Override
    public List<ProductVo> listRecommendedProduct() {
        List<Product> products = productRepository.findTopNByOrderByCreateTimeDesc(3);
        return productMapper.toVosWithOutProductSku(products);
    }

    @Override
    @Transactional
    public ProductVo getProductBasicInfo(int productId) {
        Optional<Product> res = productRepository.findById(productId);
        if (res.isEmpty())
            return null;
        Product product = res.get();
        return productMapper.toVo(product);
    }

    @Override
    public ProductParamVo getProductParamsByProductId(int productId) {
        Optional<ProductParam> res = productParamRepository.findByProductProductId(productId);
        if (res.isEmpty())
            return null;
        ProductParam productParam = res.get();
        return productParamMapper.toProductParamVo(productParam);
    }

    @Override
    public PageHelper<ProductCommentVo> getProductCommentsByProductId(int productId, int pageNum, int limit) {
        Pageable paging = PageRequest.of(pageNum, limit);
        Page<ProductComment> pageRes = productCommentRepository.findAllByProductProductId(productId, paging);
        List<ProductCommentVo> vos = productCommentMapper.toProductCommentVo(pageRes.getContent());

        PageHelper<ProductCommentVo> pageHelper = new PageHelper<>();
        pageHelper.setList(vos);
        pageHelper.setCount(vos.size());
        pageHelper.setPageCount(pageRes.getTotalPages());

        return pageHelper;
    }

    @Override
    public ProductCommentCounterVo getProductCommentsCounterByProductId(int productId) {
        HashMap<Integer, Long> comments = productCommentRepository.countCommentType(productId);
        long goodCommentNum = comments.get(1);
        long midCommentNum = comments.get(0);
        long badCommentNum = comments.get(-1);
        long total = goodCommentNum + midCommentNum + badCommentNum;

        double percent = (Double.parseDouble(goodCommentNum + "") /
                Double.parseDouble(total + "")) * 100;

        String percentValue = (percent + "").substring(0,
                (percent + "").lastIndexOf(".") + 3);

        return ProductCommentCounterVo.builder()
                .total(total)
                .goodTotal(goodCommentNum)
                .midTotal(midCommentNum)
                .badTotal(badCommentNum)
                .percent(percentValue)
                .build();
    }

    @Override
    public PageHelper<ProductVo> getProductsByCategoryId(int categoryId, int pageNum, int limit) {
        return productRepository.findProductsWithCheapestProductSku(categoryId, pageNum, limit);
    }

    @Override
    public List<String> listBrands(int categoryId) {
        List<String> brands = productParamRepository.findBrandByCategoryId(categoryId);
        return brands;
    }

    @Override
    public PageHelper<ProductVo> searchProducts(String keyword, int pageNum, int limit) {
        return productRepository.findProductsByNameContainingWithCheapestProductSku(keyword, pageNum, limit);
    }

    @Override
    public List<String> listBrandsByKeyword(String keyword) {
        return productParamRepository.findBrandsByKeyword("%" + keyword + "%");
    }
}

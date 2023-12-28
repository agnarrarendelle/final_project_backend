package practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.entity.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, String> {
}

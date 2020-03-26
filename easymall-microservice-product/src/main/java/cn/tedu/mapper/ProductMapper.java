package cn.tedu.mapper;

import com.jt.common.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    Integer queryTotal();
    List<Product> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    Product queryProductById(String productId);
    void productSave(Product product);
    void productUpdate(Product product);
}


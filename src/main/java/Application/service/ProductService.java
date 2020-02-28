package Application.service;

import Application.models.Merchant;
import Application.models.Product;
import Application.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public void addProduct(Merchant merchant, Product product) {
        product.setMerchantId(merchant.getId());
        if (checkProduct(product.getInventory())) productRepo.save(product);
        else throw new IllegalArgumentException("Too few products");
    }

    private boolean checkProduct(int inventory) {
        return inventory > 5;
    }

    public List<Product> getProductByMerchantId(int merchantId) {
        return productRepo.findAllByMerchantId(merchantId);
    }

    public void deleteById(Merchant merchant,int id) {
        List<Product> merchProduct = getProductByMerchantId(merchant.getId());
        Optional<Product> findProduct = merchProduct.stream().filter(k->k.getID()==id).findAny();
        findProduct.orElseThrow(IllegalAccessError::new);
        findProduct.ifPresent(productRepo::delete);
    }
}

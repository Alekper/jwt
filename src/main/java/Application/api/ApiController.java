package Application.api;

import Application.models.AuthRequest;
import Application.models.Merchant;
import Application.models.Product;
import Application.service.MerchantDbService;
import Application.service.ProductService;
import Application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ApiController {

    @Autowired
    private MerchantDbService merchantDbService;
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    //Registration of Merchant
    @PostMapping(value = "/signup")
    public String signup(@RequestBody Merchant merchant) {
        merchantDbService.addMerchant(merchant);
        return "Register was successful";
    }

    //Update Merchant's password
    @PutMapping(value = "update/password")
    public String changePass(Authentication authentication, @RequestBody String password) {
        Merchant merchant = merchantDbService.findByEmail(authentication.getName());
        merchantDbService.updatePassword(merchant, password);
        return "Password changed successfully";
    }

    //Delete Merchant
    @DeleteMapping(value = "/delete")
    public String delete(Authentication authentication) {
        Merchant merchant = merchantDbService.findByEmail(authentication.getName());
        merchantDbService.delete(merchant);
        return "Deleted Successful";
    }

    //Add Merchant product
    @PostMapping(value = "/product/add")
    public String addProduct(Authentication authentication, @RequestBody Product product) {
        Merchant merchant = merchantDbService.findByEmail(authentication.getName());
        productService.addProduct(merchant, product);
        return "Added";
    }

    //Delete product of Merchant by ID
    @DeleteMapping(value = "/product/delete/{id}")
    public String deleteProduct(Authentication authentication, @PathVariable int id) {
        Merchant merchant = merchantDbService.findByEmail(authentication.getName());
        productService.deleteById(merchant, id);
        return "Deleted Successful";
    }

    //Get All products of Merchant
    @GetMapping(value = "/products")
    public Collection<Product> getProducts(Authentication authentication) {
        Merchant merchant = merchantDbService.findByEmail(authentication.getName());
        return productService.getProductByMerchantId(merchant.getId());
    }

    //Authentication of Merchant
    @PostMapping(value = "/signin")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try { authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username / password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    //Confirmation
    @GetMapping(value = "/confirm/{token}")
    public String confirmAcc(@PathVariable String token) {
        merchantDbService.confirmAcc(token);
        return "Successful";
    }

}

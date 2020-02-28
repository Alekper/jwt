package Application.service;

import Application.models.Merchant;
import Application.repo.MerchantRepo;
import Application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantDbService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MerchantRepo merchantRepo;


    public void addMerchant(Merchant merchant) {
        if (checkAcc(merchant)) throw new IllegalArgumentException("User already has account");
        if (checkPass(merchant)) {
            sendMail(merchant);
            merchant.setPassword(bCryptPasswordEncoder.encode(merchant.getPassword()));
            merchantRepo.save(merchant);
        } else throw new IllegalArgumentException("Password is too weak");
    }

    private boolean checkPass(Merchant merchant) {
        return merchant.getPassword().toCharArray().length >= 6 && !merchant.getPassword().contains(" ");
    }

    private boolean checkAcc(Merchant merchant) {
        return merchantRepo.findByEmail(merchant.getEmail()).isPresent();
    }

    private void sendMail(Merchant merchant) {
        String token = jwtUtil.generateToken(merchant.getEmail());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("super-boy-2021@mail.ru");
        mailMessage.setTo(merchant.getEmail());
        mailMessage.setSubject("Confirmation");
        mailMessage.setText(
                        "\nMerchant Type: " + merchant.getType() +
                        "\nName: " + merchant.getName() +
                        "\nOwnerName: " + merchant.getOwnName() +
                        "\nAddress: " + merchant.getAddress() +
                        "\nPhone Number: " + merchant.getPhone() +
                        "\nEmail address: " + merchant.getEmail() +
                        "\nTo confirm your account please follow the link: " +
                        "\nhttp://localhost:9000/confirm/" + token);

        merchant.setToken(token);
        javaMailSender.send(mailMessage);
    }

    public Merchant findByEmail(String email) {
        Optional<Merchant> merchant = merchantRepo.findByEmail(email);
        merchant.orElseThrow(IllegalAccessError::new);
        return merchant.get();
    }

    public void updatePassword(Merchant merchant, String password) {
        merchant.setPassword(bCryptPasswordEncoder.encode(password));
        merchantRepo.save(merchant);
    }

    private void updateStatus(Merchant merchant) {
        merchant.setActive(true);
        merchant.setToken("");
        merchantRepo.save(merchant);
    }

    public void delete(Merchant merchant) {
        merchantRepo.delete(merchant);
    }

    public void confirmAcc(String token) {
        updateStatus(findByToken(token));
    }

    private Merchant findByToken(String token) {
        Optional<Merchant> merchantOptional = merchantRepo.findByToken(token);
        merchantOptional.orElseThrow(IllegalAccessError::new);
        return merchantOptional.get();
    }
}

package Application.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component

public class EmailCfg {

    @Getter
    @Setter
    @org.springframework.beans.factory.annotation.Value("${spring.mail.host}")
    private String host;

    @Getter
    @Setter
    @org.springframework.beans.factory.annotation.Value("${spring.mail.port}")
    private int port;

    @Getter
    @Setter
    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String username;

    @Getter
    @Setter
    @org.springframework.beans.factory.annotation.Value("${spring.mail.password}")
    private String password;


}

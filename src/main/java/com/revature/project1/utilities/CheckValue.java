package com.revature.project1.utilities;

import com.revature.project1.annotations.Author;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Author(authorName = "Leo Schaffner",
        description = "CheckValue class contains methods for checking validity of Postman inputs")
@Component
public class CheckValue {
    public boolean checkNegativeValue(int value) { return (value>=0); } // Returns true if positive, false if negative

    public boolean checkStock(int qoh) { return (qoh>0); } // Returns true if greater than 0, false if less

    public boolean checkOWASPEmail(String email) { // Returns true if email is valid, false if not
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return Pattern.compile(EMAIL_REGEX) // Utilizes Regex, source: https://owasp.org/www-community/OWASP_Validation_Regex_Repository
                .matcher(email)
                .matches();
    }
}

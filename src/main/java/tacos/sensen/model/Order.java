package tacos.sensen.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Order {
    private Long id;
    private String placedAt;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @NotBlank(message = "Zip code is required")
    private String zip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCvv;
    private Taco design;
}

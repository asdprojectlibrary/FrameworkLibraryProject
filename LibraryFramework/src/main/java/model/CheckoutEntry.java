package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
final public class CheckoutEntry extends BaseModel {

    private   String id;

    private   BookCopy checkoutItem;

    private   Member member;

    private   ZonedDateTime dueDate;

    private   ZonedDateTime checkoutDate;
}



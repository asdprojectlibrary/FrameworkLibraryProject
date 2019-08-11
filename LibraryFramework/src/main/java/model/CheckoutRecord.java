package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
final public class CheckoutRecord extends BaseModel {

    private   Member member;

    private List<CheckoutEntry> checkoutEntries;
}

package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
final public class Member extends Person {

    private  CheckoutRecord checkoutRecord;

    public Member(String memberId, String fname, String lname, String tel,Address add) {
        super(fname,lname, tel, add);
        this.setId(memberId);
        checkoutRecord = new CheckoutRecord();        // library member with zero checkout entries
    }
}

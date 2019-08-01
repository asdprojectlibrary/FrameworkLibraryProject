package dataaccess.dao;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import business.CheckoutEntry;
import business.CheckoutRecord;

public class CheckoutRecordRepository extends ObjectRepository
{

    public static List<CheckoutEntry> getEntries(String isbn) {
        Map<String, CheckoutRecord> records = dbAccess.readCheckoutRecordMap();
        return records
            .values()
            .stream()
            .flatMap(record->record.getCheckoutEntries(isbn).stream())
            .collect(Collectors.toList());
    } 
    
   public static List<CheckoutEntry> getOverdueCopies(String isbn) {
        return getEntries(isbn)
            .stream()
            .filter(entry-> ZonedDateTime.now().toEpochSecond() > entry.getDueDate().toEpochSecond()
                && entry.getCheckoutItem().isAvailable() == false)
            .collect(Collectors.toList());
    }
   
   public static void updateRecord(CheckoutRecord record) {
       dbAccess.updateCheckoutRecord(record);
   }
}

package com.crimsonlogic.turfmanagementsystem.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private String userwalletId;   // Custom generated wallet ID with "WALL" prefix
    private String userId;         // Foreign key reference to the user (String ID)
    private BigDecimal balance;    // Balance in the wallet
}

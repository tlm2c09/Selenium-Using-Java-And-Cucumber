package pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @Builder
public class Account implements Serializable {
    int id;
    String type;
    String balance;

    public Account(int id, String type, String balance) {
        this.id = id;
        this.type = type;
        this.balance = balance;
    }
}

package ciprian.licenta.quickticket.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SystemAdmin")
public class SystemAdmin extends User {

    public SystemAdmin() {
        super();
    }

    public SystemAdmin(String username, String password, String email, UserRole role) {
        super(username, password, email, role);
    }
}

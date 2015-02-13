

package domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author kelli
 */
@PersistenceUnit(unitName = "tracePU")
@Entity(name = "users")
public class User {
    @Id
    private int userId;
    private String userName;
    private String password;
    private String saltValue;
    
    
    
}

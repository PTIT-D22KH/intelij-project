package duongvct.app.utls;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN", "admin"),
    ROLE_USER("ROLE_USER", "user"),
//    ROLE_EMPLOYEE("ROLE_EMPLOYEE", "employee"),
//    ROLE_GUEST("ROLE_GUEST", "guest")
    ;
    private String id, name;


    Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return id;
    }
}

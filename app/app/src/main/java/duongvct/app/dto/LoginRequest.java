package duongvct.app.dto;

public class LoginRequest {
    private String email;
    private String password;

    private Integer delay;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
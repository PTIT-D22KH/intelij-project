package duongvct.app.utls;

public class CustomResponse<T> {
    private String message;
    private T data;

    private int status;

    public CustomResponse(String message, T data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public CustomResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public CustomResponse(T data, int status) {
        this.data = data;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

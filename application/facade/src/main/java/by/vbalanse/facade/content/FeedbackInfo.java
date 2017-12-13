package by.vbalanse.facade.content;

import by.vbalanse.facade.article.HasIdInfo;

/**
 * Created by Zhuk on 12.10.15.
 */
public class FeedbackInfo extends HasIdInfo {

    private String firstName;
    private String email;
    private String message;
    private Long userId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleUserResponseModel {

    private UserData data;
    private SupportData support;


    @Data
    public static class UserData {
        private int id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String avatar;

    }

    @Data
    public static class SupportData {
        private String url;
        private String text;
    }

}
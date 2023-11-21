package models.create_user;


import lombok.Data;

@Data
public class CreateSuccessfulUserResponseModel {

    private String name, job, id, createdAt;

}
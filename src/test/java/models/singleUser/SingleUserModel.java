package models.singleUser;

import lombok.Data;

@Data
public class SingleUserModel {


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getData(String values) {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    String first_name, data;

}
//{
//        "data": {
//        "id": 2,
//        "email": "janet.weaver@reqres.in",
//        "first_name": "Janet",
//        "last_name": "Weaver",
//        "avatar": "https://reqres.in/img/faces/2-image.jpg"
//        },
//        "support": {
//        "url": "https://reqres.in/#support-heading",
//        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
//        }
//        }
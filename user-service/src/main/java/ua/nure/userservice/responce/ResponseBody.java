package ua.nure.userservice.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

    ResponseType responseType;
    Object result;
    Map<String, String> errors;

    {
        responseType = ResponseType.SUCCESS;
        errors = new HashMap<>();
    }

    public ResponseBody(Object result) {
        this.result = result;
    }

    public enum ResponseType {
        SUCCESS, VALIDATION_FAILED, HTTP_ERROR
    }

}

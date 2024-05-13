package ua.nure.userservice.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

    ResponseType responseType;
    Object body;
    Map<String, String> errors;

    {
        responseType = ResponseType.SUCCESS;
        errors = new HashMap<>();
    }

    public ResponseBody(Object body) {
        this.body = body;
    }
}

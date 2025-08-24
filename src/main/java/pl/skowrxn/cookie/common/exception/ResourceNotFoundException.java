package pl.skowrxn.cookie.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String objectName;
    private String fieldValue;
    private String fieldName;
    private UUID id;

    public ResourceNotFoundException(String objectName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s: %s", objectName, fieldName, fieldValue));
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String objectName, String fieldValue) {
        super(String.format("%s not found with id: %s", objectName, fieldValue));
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}

package protocol;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Session {

    private String userId;
    private String userName;

    @Tolerate
    public Session(){}
}

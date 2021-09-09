import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InputItem {

    private String itemName;

    private Integer volume;

}

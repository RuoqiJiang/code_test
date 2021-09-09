import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BundleConfig {

    private Integer volume;

    private Double price;

}

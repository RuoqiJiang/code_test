import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BundleConfigs {

    private List<BundleConfig> imageBundle;

    private List<BundleConfig> audioBundle;

    private List<BundleConfig> videoBundle;

}

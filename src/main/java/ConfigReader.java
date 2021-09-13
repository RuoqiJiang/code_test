import com.logTest.log4j.test.Log4jTest01;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class ConfigReader {
    public static final Logger log = LoggerFactory.getLogger(Log4jTest01.class);

    public static void main(String[] args){
        getConfigs();
    }


    private final static BundleConfigs EMPTY_BUNDLE = new BundleConfigs()
            .setImageBundle(new ArrayList<>())
            .setAudioBundle(new ArrayList<>())
            .setVideoBundle(new ArrayList<>());

    public static BundleConfigs getConfigs() {
        Map<String, Object> raw = null;

        try {
            raw = new Yaml().load(ConfigReader.class.getResourceAsStream("/bundleConfigs.yml"));
        } catch (Exception e) {
            log.error("Reader file error" + e.getMessage());
            return EMPTY_BUNDLE;
        }
        if (raw == null || raw.size() == 0) {
            log.error("Empty file");
            return EMPTY_BUNDLE;
        }
        return new BundleConfigs()
                .setImageBundle(getBundles(raw.get("imageBundle")))
                .setAudioBundle(getBundles(raw.get("audioBundle")))
                .setVideoBundle(getBundles(raw.get("videoBundle")));
    }

     private static List<BundleConfig> getBundles(Object obj) {
        if (obj != null) {
            try {
                return ((List<Map<String,Number>>) obj).stream()
                        .map(bundle -> new BundleConfig()
                              .setVolume((Integer) bundle.get("volume"))
                              .setPrice((Double) bundle.get("price")))
                        .collect(Collectors.toList());
            } catch (Exception ignored) {}
            }
        return new ArrayList<>();
        }
     }





import java.util.*;
import java.util.stream.Collectors;

public class Calculator {

    private static final String LINE_SEPARATOR = "\n";
    private static final String SPACE = " ";
    private static final String IMG = "IMG";
    private static final String FLAC = "FLAC";
    private static final String VID = "VID";

    public static void main (String[] args) {

    }


    private static int desc(BundleConfig config1, BundleConfig config2) {
        return config2.getVolume().compareTo(config1.getVolume());
    }

    public String calculate(String input) {
        if (input == null || input.length() == 0) {
            return "no result";
        }
        return process(getInputItems(input));
    }

    private String process(List<InputItem> inputItems) {
        StringBuilder stringBuilder = new StringBuilder();

        inputItems.stream()
                .map(this::calculateItem)
                .filter(Objects::nonNull)
                .forEach(stringBuilder::append);

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private String calculateItem(InputItem inputItem) {
        BundleConfigs configs = ConfigReader.getConfigs();

        switch (inputItem.getItemName()) {
            case IMG:
                return calculateItem(inputItem, configs.getImageBundle());
            case FLAC:
                return calculateItem(inputItem, configs.getAudioBundle());
            case VID:
                return calculateItem(inputItem, configs.getVideoBundle());
            default:
                return null;
        }
    }

    private String calculateItem(InputItem inputItem, List<BundleConfig> configBundles) {
        String str = initString(inputItem);

        List<Integer> volumeList = calculateVolume(inputItem.getVolume(), configBundles);

        return str + total(volumeList, configBundles);
    }

    private String total(List<Integer> volumeList, List<BundleConfig> configBundles) {
        StringBuilder stringBuilder = new StringBuilder();
        double total = 0;

        for (int i = 0; i < configBundles.size(); i++) {
            if (volumeList.get(i) > 0) {
                double bundlePrice = volumeList.get(i) * configBundles.get(i).getPrice();
                total += bundlePrice;
                stringBuilder.append(each(volumeList.get(i), configBundles.get(i).getVolume(), bundlePrice));
            }
        }

        return "$" + total + LINE_SEPARATOR + stringBuilder;
    }

    private String each(int volume, int bundle, double price) {
        return "\t" +
                volume +
                " x " +
                bundle +
                " $" +
                price +
                LINE_SEPARATOR;
    }

    private List<Integer> calculateVolume(int inputVolume, List<BundleConfig> bundles) {
        Iterator<BundleConfig> iterator = bundles.iterator();
        List<Integer> volumeList = new ArrayList<>();

        while (iterator.hasNext()) {
            BundleConfig bundleConfig = iterator.next();
            int volume = inputVolume / bundleConfig.getVolume();
            volumeList.add(volume);
            inputVolume -= volume * bundleConfig.getVolume();
        }

        int last = volumeList.get(volumeList.size() - 1);
        volumeList.set(volumeList.size() - 1, inputVolume > 0 ? last + 1 : last);

        return volumeList;
    }

    private String initString(InputItem inputItem) {
        return inputItem.getVolume() +
                SPACE +
                inputItem.getItemName() +
                SPACE;
    }

    private List<InputItem> getInputItems(String input) {
        String[] lines = input.split(LINE_SEPARATOR);
        return Arrays.stream(lines)
                .map(this::getLine)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private InputItem getLine(String line) {
        String[] lines = line.split(SPACE);

        if (lines.length < 2) {
            return null;
        }

        return new InputItem().setItemName(lines[1]).setVolume(Integer.valueOf(lines[0]));
    }

}


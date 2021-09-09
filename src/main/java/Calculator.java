import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {

    private static final BundleConfigs configs = new BundleConfigs();

    private static final String LINE_SEPARATOR = "\n";
    private static final String SPACE = " ";

    private static final String IMG = "IMG";
    private static final String FLAC = "FLAC";
    private static final String VID = "VID";

    static {
        configs.setImageBundle(
                        Stream.of(new BundleConfig().setVolume(5).setPrice(450d),
                                new BundleConfig().setVolume(10).setPrice(800d))
                                .sorted(Calculator::desc)
                                .collect(Collectors.toList()))
                .setAudioBundle(
                        Stream.of(new BundleConfig().setVolume(3).setPrice(427.5d),
                                new BundleConfig().setVolume(6).setPrice(810d),
                                new BundleConfig().setVolume(9).setPrice(1147.5d))
                                .sorted(Calculator::desc)
                                .collect(Collectors.toList()))
                .setVideoBundle(
                        Stream.of(new BundleConfig().setVolume(3).setPrice(570d),
                                new BundleConfig().setVolume(5).setPrice(900d),
                                new BundleConfig().setVolume(9).setPrice(1530d))
                                .sorted(Calculator::desc)
                                .collect(Collectors.toList()));
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

    private StringBuilder calculateItem(InputItem inputItem) {
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

    private StringBuilder calculateItem(InputItem inputItem, List<BundleConfig> bundles) {
        StringBuilder stringBuilder = initString(inputItem);

        List<Integer> volumeList = calculateVolume(inputItem.getVolume(), bundles);

        return stringBuilder.append(total(volumeList, bundles));
    }

    private StringBuilder total(List<Integer> volumeList, List<BundleConfig> bundles) {
        StringBuilder stringBuilder = new StringBuilder();
        double total = 0;

        for (int i = 0; i < bundles.size(); i++) {
            if (volumeList.get(i) > 0) {
                double bundlePrice = volumeList.get(i) * bundles.get(i).getPrice();
                total += bundlePrice;
                stringBuilder.append(each(volumeList.get(i), bundles.get(i).getVolume(), bundlePrice));
            }
        }

        return new StringBuilder().append("$").append(total).append(LINE_SEPARATOR).append(stringBuilder);
    }

    private StringBuilder each(int volume, int bundle, double price) {
        return new StringBuilder()
                .append("\t")
                .append(volume)
                .append(" x ")
                .append(bundle)
                .append(" $")
                .append(price)
                .append(LINE_SEPARATOR);
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

    private StringBuilder initString(InputItem inputItem) {
        return new StringBuilder()
                .append(inputItem.getVolume())
                .append(SPACE)
                .append(inputItem.getItemName())
                .append(SPACE);
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

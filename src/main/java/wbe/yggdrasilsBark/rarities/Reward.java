package wbe.yggdrasilsBark.rarities;

public class Reward {

    private String suffix;

    private String command;

    public Reward(String suffix, String command) {
        this.suffix = suffix;
        this.command = command;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

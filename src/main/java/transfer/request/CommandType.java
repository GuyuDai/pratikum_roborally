package transfer.request;


public enum CommandType {
    CREATE_GAME("create game"),
    JOIN_GAME("join"),
    LEAVE_GAME("leave"),
    LEAVE_SERVER("bye"),
    START_GAME("start game"),
    CURRENT_SCORE("score"),
    NO_SUCH_COMMAND_FOUND("No command found"),
    SELECT_CARD("play"), //TODO card names !play Guard
    SELECT_PLAYER("select"); //TODO Player.getName() !select A
    private final String commandIdentification;
    private CommandType(String commandIdentification){
        this.commandIdentification = commandIdentification;
    }

    public String getCommandIdentification() {
        return commandIdentification;
    }
}

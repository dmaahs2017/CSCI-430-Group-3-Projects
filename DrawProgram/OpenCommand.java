public class OpenCommand extends Command {
  private String fileName;
  public OpenCommand(String fileName) {
    this.fileName = fileName;
  }
  public void execute() {
    model.retrieve(fileName);
  }
  public  boolean undo() {
    return false;
  }
  public  boolean redo() {
    return false;
  }
}

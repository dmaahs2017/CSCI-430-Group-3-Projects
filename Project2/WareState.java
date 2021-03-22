public abstract class WareState {
  protected static WareContext context;
  protected WareState() {
    //context = WareContext.instance();
  }
  public abstract void run();
}

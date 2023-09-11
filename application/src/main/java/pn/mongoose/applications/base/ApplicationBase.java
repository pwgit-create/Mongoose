package pn.mongoose.applications.base;


public abstract class ApplicationBase implements Runnable {

    /**
     * The argument(s) for the given application
     */
    protected final String[] args;
    public ApplicationBase(String[] args) {
        this.args = args;
    }

    /**
     * Run a given application
     */
    protected abstract void RunApplication(String... args);
}

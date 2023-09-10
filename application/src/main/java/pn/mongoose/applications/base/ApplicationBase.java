package pn.mongoose.applications.base;

import pn.mongoose.applications.model.ApplicationResponse;

public abstract class ApplicationBase implements Runnable {

    protected final String args[];
    public ApplicationBase(String args[]) {
        this.args = args;
    }

    /**
     * Run a given application
     * @param args
     */
    protected abstract void RunApplication(String... args);
}

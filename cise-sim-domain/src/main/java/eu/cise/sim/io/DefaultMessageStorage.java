package eu.cise.sim.io;

public class DefaultMessageStorage implements MessageStorage<Object> {
    private  Object object;

    @Override
    public void store(Object object) {
        this.object = object;
    }

    @Override
    public Object read() {
        return object;
    }

    @Override
    public boolean delete(Object message) {
        if (this.object.equals(message)) {
            this.object = null;
            return true;
        }
        return false;
    }

    public boolean isObjectNull() {
        return object == null;
    }
}
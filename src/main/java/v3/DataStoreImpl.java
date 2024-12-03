package v3;

import v3.inter.IDataStore;

public class DataStoreImpl implements IDataStore {
    @Override
    public String getData(Long id) {
        return "You called the method in the first impl!";
    }
}

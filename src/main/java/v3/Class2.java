package v3;

import v3.inter.IDataStore;

public class Class2 {
    private final IDataStore dataStore;

    public Class2(IDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public String getData(Long id) {
        return this.dataStore.getData(id);
    }
}

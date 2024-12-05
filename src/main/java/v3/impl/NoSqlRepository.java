package v3.impl;

import v3.inter.IDataRepository;

public class NoSqlRepository implements IDataRepository {
    @Override
    public String getDataById(Long id) {
        return "You called the method in NoSQL!";
    }
}

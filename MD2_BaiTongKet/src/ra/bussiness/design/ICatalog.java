package ra.bussiness.design;

import java.util.List;

public interface ICatalog<T,E> extends IShop<T,E>{
    List<T> findAll();
    T findByName(E name);
}

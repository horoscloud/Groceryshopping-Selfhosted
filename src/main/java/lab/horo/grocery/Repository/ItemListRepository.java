package lab.horo.grocery.Repository;

import lab.horo.grocery.Entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemListRepository extends JpaRepository<ItemList, Long> {

    Optional<ItemList> findItemListByName(String name);
}

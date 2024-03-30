package tw.appworks.school.example.stylish.repository.marketing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.campaign.Campaign;
import tw.appworks.school.example.stylish.model.marketing.HotProduct;

import java.util.List;

@Repository
public interface MarketingRepository extends JpaRepository<Campaign, Long> {

    @Query("""
                SELECT hp FROM HotProduct hp
            """)
    List<HotProduct> findAllHotProduct();

}

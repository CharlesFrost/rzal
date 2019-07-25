package online.patologia.springbootmatchmatching.repo;

import online.patologia.springbootmatchmatching.model.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatchPlayerRepo extends JpaRepository<MatchPlayer,Long> {
    List<MatchPlayer> findByMatchId(Long id);
    MatchPlayer findByUsername(String s);
    @Transactional
    @Query(value="SELECT * FROM match_player WHERE match_id=?1 AND username=?2",nativeQuery = true)
    MatchPlayer findByWszystko(Long l,String s);
    @Transactional
    @Modifying
    @Query(value="DELETE FROM match_player WHERE match_id=?1 AND username=?2",nativeQuery = true)
    void deleteMojWlasny(Long l,String s);
    @Transactional
    @Query(value="SELECT count(*) FROM match_player WHERE match_id=?1",nativeQuery = true)
    int policz(Long l);
}

package online.patologia.springbootmatchmatching.model;

import javax.persistence.*;

@Entity
public class MatchPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long matchId;
    private String username;

    public MatchPlayer(Long matchId, String username) {
        this.matchId = matchId;
        this.username = username;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public MatchPlayer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
